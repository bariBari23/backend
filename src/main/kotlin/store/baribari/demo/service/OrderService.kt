package store.baribari.demo.service

import org.springframework.data.domain.Pageable
import store.baribari.demo.dto.order.request.CancelOrderItemResponseDto
import store.baribari.demo.dto.order.request.CreateOrderRequestDto
import store.baribari.demo.dto.order.response.FindAllOrderResponseDto
import store.baribari.demo.dto.order.response.FindOneOrderResponseDto
import store.baribari.demo.dto.order.response.OrderItemDto

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

    // 관리자용 함수

    fun orderedOrder(
        orderId: Long
    ): FindOneOrderResponseDto

    fun orderedOrderItem(
        orderItemId: Long
    ): OrderItemDto


    // TODO: 실제 서비스에서는 쓰이지 않는다.
    fun completeOrder(
        orderId: Long
    ): FindOneOrderResponseDto


    fun completeOrderItem(
        orderItem: Long
    ): OrderItemDto


    fun forcePickupOrder(
        orderId: Long
    ): Long


    fun forcePickUpOrderItem(
        orderItemId: Long
    ): Long

}
