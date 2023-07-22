package store.baribari.demo.dto

import store.baribari.demo.common.enums.OrderStatus
import store.baribari.demo.model.order.OrderItem
import java.time.LocalDateTime

data class MyOrderItemElementDto(
    val orderItemId: Long,
    val dosirkaName: String,
    val storeName: String,
    val dosirakImage: String,
    val price: Int,
    val count: Int,
    val total: Int,
    val status: OrderStatus,
    val isReviewed: Boolean,
    val estimatedPickUpTime: String,
    val pickupTime: LocalDateTime?,
    val orderCreatedAt: LocalDateTime?,
    // 추후 상점 id도 넣어줘야하나?
) {
    companion object {
        fun fromOrderItem(orderItem: OrderItem): MyOrderItemElementDto {
            return MyOrderItemElementDto(
                orderItemId = orderItem.id!!,
                dosirkaName = orderItem.dosirak.name,
                dosirakImage = orderItem.dosirak.mainImageUrl,
                storeName = orderItem.dosirak.store.name,
                price = orderItem.dosirak.price,
                count = orderItem.count,
                total = orderItem.count * orderItem.dosirak.price,
                status = orderItem.status,
                isReviewed = orderItem.isReviewed,
                pickupTime =  orderItem.pickupTime,
                estimatedPickUpTime = orderItem.estimatedPickUpTime,
                orderCreatedAt = orderItem.order.createdAt,
            )
        }
    }
}


