package store.baribari.demo.dto.review.response

import store.baribari.demo.model.Review
import store.baribari.demo.model.User

data class ReadReviewByStoreResponseDto(
    val reviewCount: Int,
    val reviewList: List<ReadOneReviewResponseDto> = mutableListOf(),
) {
    companion object {
        fun fromReviewList(
            reviewList: List<Review>,
            user: User?,
        ): ReadReviewByStoreResponseDto {
            return ReadReviewByStoreResponseDto(
                reviewCount = reviewList.size,
                reviewList = reviewList.map { ReadOneReviewResponseDto.fromReview(it, user) }
            )
        }
    }
}
