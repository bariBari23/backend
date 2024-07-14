package store.baribari.demo.domain.review.service

import store.baribari.demo.domain.review.dto.request.CreateReviewRequestDto
import store.baribari.demo.domain.review.dto.response.CreateReviewResponseDto
import store.baribari.demo.domain.review.dto.response.ReadOneReviewResponseDto
import store.baribari.demo.domain.review.dto.response.ReadReviewByStoreResponseDto

interface ReviewService {
    fun createReview(
        userEmail: String,
        createReviewRequestDto: CreateReviewRequestDto,
    ): CreateReviewResponseDto

    fun readOneReview(
        userEmail: String?,
        reviewId: Long,
    ): ReadOneReviewResponseDto

    fun readReviewByStore(
        userEmail: String?,
        storeId: Long,
    ): ReadReviewByStoreResponseDto
}
