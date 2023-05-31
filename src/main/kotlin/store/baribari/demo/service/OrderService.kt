package store.baribari.demo.service

import store.baribari.demo.dto.CreateOrderRequestDto

interface OrderService {
    fun getOrderList(
        username: String,
    )

    fun getOneOrder(
        username: String,
        orderId: Long,
    )

    fun createOrder(
        username: String,
        createOrderRequestDto: CreateOrderRequestDto
    ): Long

    fun cancelOrderItem(
        username: String,
        orderId: Long,
        orderItemId: Long,
    ): Long
}
