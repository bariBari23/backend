package store.baribari.demo.domain.cart.dto.request

import javax.validation.constraints.Max
import javax.validation.constraints.Positive

data class RequestedItemList(
    @field:Positive
    val dosirakId: Long,
    @field:Positive
    @field:Max(3)
    val amount: Int,
)
