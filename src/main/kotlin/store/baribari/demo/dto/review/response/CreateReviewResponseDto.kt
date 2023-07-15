package store.baribari.demo.dto.review.response

import store.baribari.demo.model.Review

data class CreateReviewResponseDto(
    val reviewId: Long,
    val orderItemId: Long,
    val content: String,
    val rating: Int,
    val photoList: List<String> = listOf(),
    val tags: List<String> = listOf(),
    val writer: String,
    val createdAt: String,
) {
    companion object {
        fun fromReview(review: Review): CreateReviewResponseDto {
            return CreateReviewResponseDto(
                reviewId = review.id!!,
                orderItemId = review.orderItem.id!!,
                content = review.content,
                rating = review.rating,
                photoList = review.photoList,
                tags = review.tags.map { it.name },
                writer = review.writer.nickname,
                createdAt = review.createdAt.toString(),
            )
        }
    }
}
