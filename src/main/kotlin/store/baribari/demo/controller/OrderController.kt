package store.baribari.demo.controller

import org.springframework.data.domain.Pageable
import org.springframework.security.core.userdetails.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import store.baribari.demo.auth.LoginUser
import store.baribari.demo.dto.common.ApiResponse
import store.baribari.demo.dto.order.request.CancelOrderItemResponseDto
import store.baribari.demo.dto.order.request.CreateOrderRequestDto
import store.baribari.demo.dto.order.response.FindAllOrderResponseDto
import store.baribari.demo.dto.order.response.FindOneOrderResponseDto
import store.baribari.demo.service.OrderService
import javax.validation.Valid
import javax.validation.constraints.Positive


@Validated
@RestController
@RequestMapping("/api/v1/order")
class OrderController(
    private val orderService: OrderService,
) {

    // 내 주문 리스트 -> 현재 과거 전부 취소 된거 까지도
    @GetMapping("")
    fun getAllOrderList(
        @LoginUser loginUser: User,
        pageable: Pageable,

        ): ApiResponse<FindAllOrderResponseDto> {
        val data = orderService.findAllOrder(loginUser.username, pageable)

        return ApiResponse.success(data)
    }

    @GetMapping("/{orderId}")
    fun findOneOrder(
        @LoginUser loginUser: User,
        @PathVariable @Positive orderId: Long,
    ): ApiResponse<FindOneOrderResponseDto> {
        val data = orderService.findOneOrder(loginUser.username, orderId)

        return ApiResponse.success(data)
    }

    @PostMapping("")
    fun createOrder(
        @LoginUser loginUser: User,
        @RequestBody @Valid createOrderRequestDto: CreateOrderRequestDto,
    ): ApiResponse<Long> {

        val data = orderService.createOrder(loginUser.username, createOrderRequestDto)

        return ApiResponse.success(data)
    }

    @DeleteMapping("/{orderId}/{orderItemId}")
    fun cancelOrderItem(
        @LoginUser loginUser: User,
        @PathVariable @Positive orderId: Long,
        @PathVariable @Positive orderItemId: Long,
    ): ApiResponse<CancelOrderItemResponseDto> {
        // orderItem 취소하기
        val data = orderService.cancelOrderItem(loginUser.username, orderId, orderItemId)

        return ApiResponse.success(data)
    }

    @DeleteMapping("/{orderId}")
    fun cancelOrder(
        @LoginUser loginUser: User,
        @PathVariable @Positive orderId: Long,
    ): ApiResponse<Long> {
        // orderItem 취소하기
        val data = orderService.cancelOrder(loginUser.username, orderId)    // order 취소하기

        return ApiResponse.success(data)
    }

    // TODO: 주문 완료처리 API
    
}