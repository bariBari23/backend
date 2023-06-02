package store.baribari.demo.service

import org.springframework.stereotype.Service
import store.baribari.demo.common.exception.EntityNotFoundException
import store.baribari.demo.dto.CreateReviewResponseDto
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
        orderItemId: Long,
    ): CreateReviewResponseDto {
        // user와 item을 찾는다.

        val user = userRepository.findByEmail(username)
            ?: throw EntityNotFoundException("$username 에 해당하는 유저가 없습니다.")

        val orderItem = orderItemRepository.findByIdFetchOrderAndUser(orderItemId)
            ?: throw EntityNotFoundException("$orderItemId 에 해당하는 주문이 없습니다.")

        // review를 생성한다.
        TODO("NOT IMPLEMENTED")

        return CreateReviewResponseDto(1, 1)
    }
}