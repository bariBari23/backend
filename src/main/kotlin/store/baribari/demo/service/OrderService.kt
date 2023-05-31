package store.baribari.demo.service

import store.baribari.demo.dto.CancelOrderItemResponseDto
import store.baribari.demo.dto.CreateOrderRequestDto
import store.baribari.demo.dto.FindAllOrderResponseDto
import store.baribari.demo.dto.FindOneOrderResponseDto

interface OrderService {

    fun findAllOrder(
        username: String
    ): FindAllOrderResponseDto

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
    ): CancelOrderItemResponseDto

    fun cancelOrder(
        username: String,
        orderId: Long,
    ): Long

}
