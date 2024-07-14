package store.baribari.demo.domain.cart.dto.response

import javax.validation.constraints.Max
import javax.validation.constraints.Positive

data class UpdateItemQuantityResponseDto(
    @field:Positive
    val itemId: Long,
    @field:Max(3)
    @field:Positive
    val quantity: Int,
)
