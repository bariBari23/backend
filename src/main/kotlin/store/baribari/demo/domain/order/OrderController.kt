package store.baribari.demo.domain.order

import org.springframework.data.domain.Pageable
import org.springframework.security.core.userdetails.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import store.baribari.demo.common.dto.ApiResponse
import store.baribari.demo.domain.auth.LoginUser
import store.baribari.demo.domain.order.dto.request.CancelOrderItemResponseDto
import store.baribari.demo.domain.order.dto.request.CreateOrderRequestDto
import store.baribari.demo.domain.order.dto.response.FindOneOrderResponseDto
import store.baribari.demo.domain.order.dto.response.MyOrderResponseDto
import store.baribari.demo.domain.order.dto.response.OrderItemDto
import store.baribari.demo.domain.order.service.OrderService
import javax.validation.Valid
import javax.validation.constraints.Positive

@Validated
@RestController
@RequestMapping("/api/v1/order")
class OrderController(
    private val orderService: OrderService,
) {
    // TODO: 주문 상태 변경하는 api는 api 네이밍 이상함
    // 내 주문 리스트 -> 현재 과거 전부 취소 된거 까지도
    // TODO: 쿼리 다듬기
    @GetMapping("")
    fun findMyOrder(
        @LoginUser loginUser: User,
        pageable: Pageable,
    ): ApiResponse<MyOrderResponseDto> {
        val data = orderService.findMyOrder(loginUser.username, pageable)

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
        val data = orderService.cancelOrder(loginUser.username, orderId) // order 취소하기

        return ApiResponse.success(data)
    }

    // 관리자용 함수

    @PutMapping("ordered/order/{orderId}")
    fun orderedOrder(
        @PathVariable @Positive orderId: Long,
    ): ApiResponse<FindOneOrderResponseDto> {
        // orderItem complete로 변환
        val data = orderService.orderedOrder(orderId) // order 취소하기

        return ApiResponse.success(data)
    }

    @PutMapping("ordered/orderitem/{orderId}")
    fun orderedOrderItem(
        @PathVariable @Positive orderId: Long,
    ): ApiResponse<OrderItemDto> {
        // orderItem complete로 변환
        val data = orderService.orderedOrderItem(orderId) // order 취소하기

        return ApiResponse.success(data)
    }

    @PutMapping("complete/order/{orderId}")
    fun forceCompleteOrder(
        @PathVariable @Positive orderId: Long,
    ): ApiResponse<FindOneOrderResponseDto> {
        // orderItem complete로 변환
        val data = orderService.completeOrder(orderId) // order 취소하기

        return ApiResponse.success(data)
    }

    @PutMapping("complete/orderitem/{orderItemId}")
    fun forceCompleteOrderItem(
        @PathVariable @Positive orderItemId: Long,
    ): ApiResponse<OrderItemDto> {
        // orderItem complete로 변환
        val data = orderService.completeOrderItem(orderItemId) // order 취소하기

        return ApiResponse.success(data)
    }

    // 강제로 pickup 상태로 만들어주는 함수

    @PutMapping("pickup/order/{orderId}")
    fun forcePickupOrder(
        @PathVariable @Positive orderId: Long,
    ): ApiResponse<Long> {
        // orderItem 취소하기
        val data = orderService.forcePickupOrder(orderId) // order 취소하기

        return ApiResponse.success(data)
    }

    @PutMapping("pickup/orderitem/{orderItemId}")
    fun forcePickupOrderItem(
        @PathVariable @Positive orderItemId: Long,
    ): ApiResponse<Long> {
        // orderItem 취소하기
        val data = orderService.forcePickUpOrderItem(orderItemId) // order 취소하기

        return ApiResponse.success(data)
    }
}
