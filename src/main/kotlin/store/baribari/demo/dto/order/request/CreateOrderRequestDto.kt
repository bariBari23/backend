package store.baribari.demo.dto.order.request

import store.baribari.demo.common.enums.PayMethod
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateOrderRequestDto(
    @field: NotBlank
    val orderDemand: String,
    val orderPhoneNumber: String,
    @field: NotBlank
    val estimatedPickUpTime: String,
    @field: NotNull
    val payMethod: PayMethod,
)
