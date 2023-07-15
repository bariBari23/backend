package store.baribari.demo.dto.review.response

import store.baribari.demo.dto.order.response.OrderItemDto
import store.baribari.demo.model.Review
import store.baribari.demo.model.User

data class ReadOneReviewResponseDto(
    // TODO: 도시락도 여기 정보에 넣는 것이 좋아보임
    val reviewId: Long,
    val orderItem: OrderItemDto,
    val content: String,
    val rating: Int,
    val mainImageUrl: String,
    val photoList: List<String> = listOf(),
    val tags: List<String> = listOf(),
    val createdAt: String,
    val isWriter: Boolean = false,
    val reviewWriterName: String,
    val reviewWriterProfileImageUrl: String?,
) {
    companion object {
        fun fromReview(review: Review, user: User?): ReadOneReviewResponseDto {
            return ReadOneReviewResponseDto(
                reviewId = review.id!!,
                orderItem = OrderItemDto.fromOrderItem(review.orderItem),
                content = review.content,
                rating = review.rating,
                mainImageUrl = review.mainImageUrl,
                photoList = review.photoList,
                tags = review.tags.map { it.name },
                createdAt = review.createdAt.toString(),
                isWriter = (user == review.writer),
                reviewWriterName = review.writer.nickname,
                reviewWriterProfileImageUrl = review.writer.profileImageUrl,
            )
        }
    }
}
