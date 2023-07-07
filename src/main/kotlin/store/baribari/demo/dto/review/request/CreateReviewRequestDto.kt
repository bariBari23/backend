package store.baribari.demo.dto.review.request

import store.baribari.demo.common.enums.ReviewCategory
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive
data class CreateReviewRequestDto(
        @field: Positive
        val orderItemId: Long,
        @field: NotBlank
        val content: String,
        @field: Positive
        val rating: Int,
        val photoList: List<String>,
        val tags: List<ReviewCategory>,
)
