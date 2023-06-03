package store.baribari.demo.service

import store.baribari.demo.dto.review.request.CreateReviewRequestDto
import store.baribari.demo.dto.review.response.CreateReviewResponseDto
import store.baribari.demo.dto.review.response.ReadOneReviewResponseDto

interface ReviewService {
    fun createReview(
        username: String,
        createReviewRequestDto: CreateReviewRequestDto
    ): CreateReviewResponseDto

    fun readOneReview(
        username: String?,
        reviewId: Long
    ): ReadOneReviewResponseDto

}
