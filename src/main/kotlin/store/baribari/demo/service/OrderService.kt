package store.baribari.demo.service

import org.springframework.data.domain.Pageable
import store.baribari.demo.dto.order.request.CancelOrderItemResponseDto
import store.baribari.demo.dto.order.request.CreateOrderRequestDto
import store.baribari.demo.dto.order.response.FindOneOrderResponseDto
import store.baribari.demo.dto.order.response.MyOrderResponseDto
import store.baribari.demo.dto.order.response.OrderItemDto

interface OrderService {

    fun findMyOrder(
        userEmail: String,
        pageable: Pageable
    ): MyOrderResponseDto

    fun findOneOrder(
        userEmail: String,
        orderId: Long,
    ): FindOneOrderResponseDto

    fun createOrder(
        userEmail: String,
        createOrderRequestDto: CreateOrderRequestDto
    ): Long

    fun cancelOrderItem(
        userEmail: String,
        orderId: Long,
        orderItemId: Long,
    ): CancelOrderItemResponseDto

    fun cancelOrder(
        userEmail: String,
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
