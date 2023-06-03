package store.baribari.demo.service

import store.baribari.demo.dto.review.request.CreateReviewRequestDto
import store.baribari.demo.dto.review.response.CreateReviewResponseDto
import store.baribari.demo.dto.review.response.ReadOneReviewResponseDto
import store.baribari.demo.dto.review.response.ReadReviewByStoreResponseDto

interface ReviewService {
    fun createReview(
        username: String,
        createReviewRequestDto: CreateReviewRequestDto
    ): CreateReviewResponseDto

    fun readOneReview(
        username: String?,
        reviewId: Long
    ): ReadOneReviewResponseDto

    fun readReviewByStore(
        username: String?,
        storeId: Long
    ): ReadReviewByStoreResponseDto

}
