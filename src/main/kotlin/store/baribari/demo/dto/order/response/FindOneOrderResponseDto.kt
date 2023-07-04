package store.baribari.demo.dto.order.response

import store.baribari.demo.common.enums.OrderStatus
import store.baribari.demo.common.enums.PayMethod
import store.baribari.demo.model.order.Order

data class FindOneOrderResponseDto(
    val orderId: Long,
    val price: Int,
    val pickUpTime: String,
    val orderDemand: String,
    val orderPhoneNumber: String,
    val status: OrderStatus,
    val payMethod: PayMethod,
    val orderItemList: List<OrderItemDto>,
) {
    companion object {
        fun fromOrder(order: Order): FindOneOrderResponseDto {
            return FindOneOrderResponseDto(
                orderId = order.id!!,
                price = order.price,
                pickUpTime = order.pickUpTime,
                orderDemand = order.orderDemand,
                orderPhoneNumber = order.orderPhoneNumber,
                status = order.status,
                payMethod = order.payMethod,
                orderItemList = order.orderItemList.map { OrderItemDto.fromOrderItem(it) },
            )
        }
    }
}
