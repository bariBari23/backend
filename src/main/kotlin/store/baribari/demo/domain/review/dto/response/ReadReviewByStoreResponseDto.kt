package store.baribari.demo.domain.review.dto.response

import store.baribari.demo.domain.review.entity.Review
import store.baribari.demo.domain.user.entity.User

data class ReadReviewByStoreResponseDto(
    val reviewCount: Int,
    val reviewList: List<ReadOneReviewResponseDto> = mutableListOf(),
) {
    companion object {
        fun fromReviewList(
            reviewList: List<Review>,
            user: User?,
        ): ReadReviewByStoreResponseDto =
            ReadReviewByStoreResponseDto(
                reviewCount = reviewList.size,
                reviewList = reviewList.map { ReadOneReviewResponseDto.fromReview(it, user) },
            )
    }
}
