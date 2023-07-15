package store.baribari.demo.dto.order.request

import store.baribari.demo.common.enums.PayMethod
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class CreateOrderRequestDto(
    @field: NotBlank
    val orderDemand: String,
    @field: Pattern(regexp = "^(|^[0-9]{3}-[0-9]{3,4}-[0-9]{4})\$")
    val orderPhoneNumber: String,
    @field: NotBlank
    val estimatedPickUpTime: String,
    @field: NotNull
    val payMethod: PayMethod,
)
