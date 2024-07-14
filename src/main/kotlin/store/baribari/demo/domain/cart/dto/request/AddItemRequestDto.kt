package store.baribari.demo.domain.cart.dto.request

import javax.validation.Valid

data class AddItemRequestDto(
    @field:Valid
    val items: List<RequestedItemList>,
)
