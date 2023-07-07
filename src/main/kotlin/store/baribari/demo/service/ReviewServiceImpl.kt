package store.baribari.demo.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import store.baribari.demo.common.exception.EntityNotFoundException
import store.baribari.demo.dto.review.request.CreateReviewRequestDto
import store.baribari.demo.dto.review.response.CreateReviewResponseDto
import store.baribari.demo.dto.review.response.ReadOneReviewResponseDto
import store.baribari.demo.dto.review.response.ReadReviewByStoreResponseDto
import store.baribari.demo.model.Review
import store.baribari.demo.repository.ReviewRepository
import store.baribari.demo.repository.StoreRepository
import store.baribari.demo.repository.UserRepository
import store.baribari.demo.repository.order.OrderItemRepository

@Service
class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository,
    private val userRepository: UserRepository,
    private val orderItemRepository: OrderItemRepository,
    private val storeRepository: StoreRepository,
) : ReviewService {

    //c
    @Transactional
    override fun createReview(
        userEmail: String,
        createReviewRequestDto: CreateReviewRequestDto
    ): CreateReviewResponseDto {
        // user와 item을 찾는다.

        val user = userRepository.findByEmail(userEmail)
            ?: throw EntityNotFoundException("$userEmail 에 해당하는 유저가 없습니다.")

        val orderItem = orderItemRepository.findByIdFetchOrderAndUser(createReviewRequestDto.orderItemId)
            ?: throw EntityNotFoundException("${createReviewRequestDto.orderItemId} 에 해당하는 주문이 없습니다.")

        // orderItem과 user가 일치하는지 확인한다.

        require(orderItem.order.user!!.id == user.id) { "해당 주문의 유저와 리뷰를 생성하는 유저가 일치하지 않습니다." }

        // orderItem에 review가 있는지 확인한다.
        // TODO: 테스트 용도로만 작동시킨다 실제 서비스에서는 주석해제해야함.
        orderItem.review()

        val review = Review(
            content = createReviewRequestDto.content,
            rating = createReviewRequestDto.rating,
            photoList = createReviewRequestDto.photoList.toMutableList(),
            tags = createReviewRequestDto.tags.toMutableList(),
            writer = user,
            orderItem = orderItem
        )
        reviewRepository.save(review)
        return CreateReviewResponseDto.fromReview(review)
    }

    @Transactional(readOnly = true)
    override fun readOneReview(
        userEmail: String?,
        reviewId: Long
    ): ReadOneReviewResponseDto {
        val user = userEmail?.let {
            userRepository.findByEmail(userEmail)
                ?: throw EntityNotFoundException("$userEmail 에 해당하는 유저가 없습니다.")
        }

        // TODO: photoList와 tags를 동시에 가져올 수 있는 방법에 대해서 강구해야한다. 
        val review = reviewRepository.findByIdFetchOrderItemAndWriter(reviewId)
            ?: throw EntityNotFoundException("$reviewId 에 해당하는 리뷰가 없습니다.")

        return ReadOneReviewResponseDto.fromReview(
            review = review,
            user = user,
        )
    }

    @Transactional(readOnly = true)
    override fun readReviewByStore(userEmail: String?, storeId: Long): ReadReviewByStoreResponseDto {

        val store = storeRepository.findByIdOrNull(storeId)
                ?: throw EntityNotFoundException("$storeId 번 가게는 존재하지 않습니다.")

        val user = userEmail?.let {
            userRepository.findByEmail(userEmail)
                ?: throw EntityNotFoundException("$userEmail 에 해당하는 유저가 없습니다.")

        }

        val reviewList = reviewRepository.findByStoreFetchOrderItemAndDosirak(storeId)?.sortedBy { it.id }
            ?: emptyList()

        return ReadReviewByStoreResponseDto.fromReviewList(
            reviewList = reviewList,
            user = user,
        )
    }
}