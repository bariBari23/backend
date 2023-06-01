package store.baribari.demo.service

import org.springframework.data.domain.Pageable
import store.baribari.demo.dto.order.request.CancelOrderItemResponseDto
import store.baribari.demo.dto.order.request.CreateOrderRequestDto
import store.baribari.demo.dto.order.response.FindAllOrderResponseDto
import store.baribari.demo.dto.order.response.FindOneOrderResponseDto

interface OrderService {

    fun findAllOrder(
        username: String,
        pageable: Pageable
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
