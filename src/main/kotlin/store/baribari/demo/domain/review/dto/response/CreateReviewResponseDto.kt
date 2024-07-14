package store.baribari.demo.domain.review.dto.response

import store.baribari.demo.domain.review.entity.Review

data class CreateReviewResponseDto(
    val reviewId: Long,
    val orderItemId: Long,
    val content: String,
    val rating: Int,
    val mainImageUrl: String?,
    val photoList: List<String> = listOf(),
    val tags: List<String> = listOf(),
    val writer: String,
    val createdAt: String,
) {
    companion object {
        fun fromReview(review: Review): CreateReviewResponseDto =
            CreateReviewResponseDto(
                reviewId = review.id!!,
                orderItemId = review.orderItem.id!!,
                content = review.content,
                rating = review.rating,
                mainImageUrl = review.mainImageUrl,
                photoList = review.photoList,
                tags = review.tags.map { it.name },
                writer = review.writer.nickname,
                createdAt = review.createdAt.toString(),
            )
    }
}
