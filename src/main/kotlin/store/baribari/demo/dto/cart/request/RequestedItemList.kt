package store.baribari.demo.dto.cart.request

import javax.validation.constraints.Positive

data class RequestedItemList(
    @field:Positive
    val dosirakId: Long,

    @field:Positive
    val amount: Int
)
