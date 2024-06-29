package store.baribari.demo.dto.cart.request

import javax.validation.Valid

data class AddItemRequestDto(
    @field: Valid
    val items: List<RequestedItemList>,
)
