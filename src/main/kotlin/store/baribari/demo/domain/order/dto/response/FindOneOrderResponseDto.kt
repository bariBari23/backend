package store.baribari.demo.domain.order.dto.response

import store.baribari.demo.common.enums.OrderStatus
import store.baribari.demo.common.enums.PayMethod
import store.baribari.demo.domain.order.entity.Order

data class FindOneOrderResponseDto(
    val orderId: Long,
    val price: Int,
    val estimatedPickUpTime: String,
    val orderDemand: String,
    val orderPhoneNumber: String,
    val status: OrderStatus,
    val payMethod: PayMethod,
    val orderItemList: List<OrderItemDto>,
) {
    companion object {
        fun fromOrder(order: Order): FindOneOrderResponseDto =
            FindOneOrderResponseDto(
                orderId = order.id!!,
                price = order.price,
                estimatedPickUpTime = order.estimatedPickUpTime,
                orderDemand = order.orderDemand,
                orderPhoneNumber = order.orderPhoneNumber,
                status = order.status,
                payMethod = order.payMethod,
                orderItemList = order.orderItemList.map { OrderItemDto.fromOrderItem(it) },
            )
    }
}
