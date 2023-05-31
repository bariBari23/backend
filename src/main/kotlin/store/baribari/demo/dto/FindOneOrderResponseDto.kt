package store.baribari.demo.dto

import store.baribari.demo.common.enums.OrderStatus

data class FindOneOrderResponseDto(
    val orderId: Long,
    val price: Int,
    val status: OrderStatus,
    val orderItemList: List<OrderItemDto>,
)
