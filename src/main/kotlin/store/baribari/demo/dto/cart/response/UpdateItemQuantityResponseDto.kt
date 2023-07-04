package store.baribari.demo.dto.cart.response

import javax.validation.constraints.Max
import javax.validation.constraints.Positive

data class UpdateItemQuantityResponseDto(
    @field: Positive
    val itemId: Long,

    @field: Max(3)
    @field: Positive
    val quantity: Int,
)
