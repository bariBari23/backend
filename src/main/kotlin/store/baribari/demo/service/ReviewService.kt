package store.baribari.demo.service

import store.baribari.demo.dto.CreateReviewResponseDto

interface ReviewService {
    fun createReview(
        username: String,
        orderItemId: Long,
    ): CreateReviewResponseDto

}
