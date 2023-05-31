package store.baribari.demo.dto

import store.baribari.demo.common.enums.OrderStatus
import store.baribari.demo.model.order.Order

data class FindOneOrderResponseDto(
    val orderId: Long,
    val price: Int,
    val status: OrderStatus,
    val orderItemList: List<OrderItemDto>,
) {
    companion object {
        fun fromOrder(order: Order): FindOneOrderResponseDto {
            return FindOneOrderResponseDto(
                orderId = order.id!!,
                price = order.price,
                status = order.status,
                orderItemList = order.orderItemList.map { OrderItemDto.fromOrderItem(it) },
            )
        }
    }
}
