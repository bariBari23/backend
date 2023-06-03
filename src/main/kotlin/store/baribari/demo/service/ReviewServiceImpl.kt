package store.baribari.demo.service

import org.springframework.stereotype.Service
import store.baribari.demo.common.exception.EntityNotFoundException
import store.baribari.demo.dto.review.request.CreateReviewRequestDto
import store.baribari.demo.dto.review.response.CreateReviewResponseDto
import store.baribari.demo.dto.review.response.ReadOneReviewResponseDto
import store.baribari.demo.model.Review
import store.baribari.demo.repository.ReviewRepository
import store.baribari.demo.repository.UserRepository
import store.baribari.demo.repository.order.OrderItemRepository

@Service
class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository,
    private val userRepository: UserRepository,
    private val orderItemRepository: OrderItemRepository,
) : ReviewService {

    //c
    override fun createReview(
        username: String,
        createReviewRequestDto: CreateReviewRequestDto
    ): CreateReviewResponseDto {
        // user와 item을 찾는다.

        val user = userRepository.findByEmail(username)
            ?: throw EntityNotFoundException("$username 에 해당하는 유저가 없습니다.")

        val orderItem = orderItemRepository.findByIdFetchOrderAndUser(createReviewRequestDto.orderItemId)
            ?: throw EntityNotFoundException("${createReviewRequestDto.orderItemId} 에 해당하는 주문이 없습니다.")

        // orderItem과 user가 일치하는지 확인한다.

        require(orderItem.order.user!!.id == user.id) { "해당 주문의 유저와 리뷰를 생성하는 유저가 일치하지 않습니다." }

        // orderItem에 review가 있는지 확인한다.
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


    override fun readOneReview(
        username: String?,
        reviewId: Long
    ): ReadOneReviewResponseDto {
        val user = username?.let {
            userRepository.findByEmail(username)
                ?: throw EntityNotFoundException("$username 에 해당하는 유저가 없습니다.")
        }

        val review = reviewRepository.findByIdFetchOrderItemAndWriter(reviewId)
            ?: throw EntityNotFoundException("$reviewId 에 해당하는 리뷰가 없습니다.")

        return ReadOneReviewResponseDto.fromReview(
            review = review,
            isOwner = (review.writer == user)
        )
    }





}