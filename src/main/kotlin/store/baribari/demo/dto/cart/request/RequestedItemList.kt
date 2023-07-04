package store.baribari.demo.dto.cart.request

import javax.validation.constraints.Max
import javax.validation.constraints.Positive

data class RequestedItemList(
    @field:Positive
    val dosirakId: Long,

    @field:Positive
    @field:Max(3)
    val amount: Int
)
