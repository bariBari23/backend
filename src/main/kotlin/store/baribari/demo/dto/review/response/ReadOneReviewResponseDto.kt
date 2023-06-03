package store.baribari.demo.dto.review.response

import store.baribari.demo.model.Review

data class ReadOneReviewResponseDto(
    val reviewId: Long,
    val orderItemId: Long,
    val content: String,
    val rating: Int,
    val photoList: List<String> = listOf(),
    val tags: List<String> = listOf(),
    val writer: String,
    val createdAt: String,
    val isOwner: Boolean = false,
) {
    companion object {
        fun fromReview(review: Review, isOwner: Boolean): ReadOneReviewResponseDto {
            return ReadOneReviewResponseDto(
                reviewId = review.id!!,
                orderItemId = review.orderItem.id!!,
                content = review.content,
                rating = review.rating,
                photoList = review.photoList,
                tags = review.tags.map { it.name },
                writer = review.writer.email,
                createdAt = review.createdAt.toString(),
                isOwner = isOwner
            )
        }
    }
}
