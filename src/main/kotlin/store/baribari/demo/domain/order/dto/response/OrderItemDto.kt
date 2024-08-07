package store.baribari.demo.domain.order.dto.response

import store.baribari.demo.common.enums.OrderStatus
import store.baribari.demo.domain.order.entity.OrderItem
import java.time.LocalDateTime

data class OrderItemDto(
    val orderItemId: Long,
    val dosirakName: String,
    val dosirakMainImageUrl: String,
    val storeName: String,
    val storeMainImageUrl: String,
    val price: Int,
    val count: Int,
    val total: Int,
    val status: OrderStatus,
    val estimatedPickUpTime: String,
    val pickupTime: LocalDateTime?,
    // 추후 상점 id도 넣어줘야하나?
) {
    companion object {
        fun fromOrderItem(orderItem: OrderItem): OrderItemDto =
            OrderItemDto(
                orderItemId = orderItem.id!!,
                dosirakName = orderItem.dosirak.name,
                dosirakMainImageUrl = orderItem.dosirak.mainImageUrl,
                storeName = orderItem.dosirak.store.name,
                storeMainImageUrl = orderItem.dosirak.store.mainImageUrl,
                price = orderItem.dosirak.price,
                count = orderItem.count,
                total = orderItem.count * orderItem.dosirak.price,
                status = orderItem.status,
                pickupTime = orderItem.pickupTime,
                estimatedPickUpTime = orderItem.estimatedPickUpTime,
            )
    }
}
