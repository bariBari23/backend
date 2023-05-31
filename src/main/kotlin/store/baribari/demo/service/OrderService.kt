package store.baribari.demo.service

import store.baribari.demo.dto.CreateOrderRequestDto
import store.baribari.demo.dto.FindAllOrderResponseDto
import store.baribari.demo.dto.FindOneOrderResponseDto

interface OrderService {

    fun findOneOrder(
        username: String,
        orderId: Long,
    ): FindOneOrderResponseDto

    fun createOrder(
        username: String,
        createOrderRequestDto: CreateOrderRequestDto
    ): Long

    fun cancelOrderItem(
        username: String,
        orderId: Long,
        orderItemId: Long,
    ): Long

    fun cancelOrder(
        username: String,
        orderId: Long,
    ): Long

    fun findAllOrder(
        username: String
    ): FindAllOrderResponseDto

}
