package store.baribari.demo.dto.review.response

import store.baribari.demo.model.Review
import store.baribari.demo.model.User

data class ReadOneReviewResponseDto(
    val reviewId: Long,
    val orderItemId: Long,
    val content: String,
    val rating: Int,
    val photoList: List<String> = listOf(),
    val tags: List<String> = listOf(),
    val createdAt: String,
    val isWriter: Boolean = false,
) {
    companion object {
        fun fromReview(review: Review, user: User?): ReadOneReviewResponseDto {
            return ReadOneReviewResponseDto(
                reviewId = review.id!!,
                orderItemId = review.orderItem.id!!,
                content = review.content,
                rating = review.rating,
                photoList = review.photoList,
                tags = review.tags.map { it.name },
                createdAt = review.createdAt.toString(),
                isWriter = (user == review.writer)
            )
        }
    }
}
