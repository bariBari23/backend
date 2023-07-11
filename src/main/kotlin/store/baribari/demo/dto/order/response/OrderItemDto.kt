package store.baribari.demo.dto.order.response

import store.baribari.demo.common.enums.OrderStatus
import store.baribari.demo.model.order.OrderItem
import java.time.LocalDateTime

data class OrderItemDto(
        val orderItemId: Long,
        val name: String,
        val price: Int,
        val count: Int,
        val total: Int,
        val status: OrderStatus,
        val pickupTime: LocalDateTime?,
    // 추후 상점 id도 넣어줘야하나?
) {
    companion object {
        fun fromOrderItem(orderItem: OrderItem): OrderItemDto {
            return OrderItemDto(
                orderItemId = orderItem.id!!,
                name = orderItem.dosirak.name,
                price = orderItem.dosirak.price,
                count = orderItem.count,
                total = orderItem.count * orderItem.dosirak.price,
                status = orderItem.status,
                pickupTime =  orderItem.pickupTime,
            )
        }
    }
}

