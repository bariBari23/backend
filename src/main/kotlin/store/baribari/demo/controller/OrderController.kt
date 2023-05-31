package store.baribari.demo.controller

import org.springframework.security.core.userdetails.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import store.baribari.demo.auth.LoginUser
import store.baribari.demo.common.util.log
import store.baribari.demo.dto.CreateOrderRequestDto
import store.baribari.demo.dto.common.ApiResponse
import store.baribari.demo.service.OrderService
import javax.validation.Valid
import javax.validation.constraints.Positive


@RestController
@RequestMapping("/api/v1/order")
class OrderController(
    private val orderService: OrderService,
) {

    @GetMapping("")
    fun getOrderList(
        @LoginUser loginUser: User,
    ) {
        // 리스트 뽑기
    }

    @GetMapping("/{orderId}")
    fun getOneOrder(
        @LoginUser loginUser: User,
    ) {
        // order 1개 찍히기
    }

    @PostMapping("")
    fun createOrder(
        @LoginUser loginUser: User,
        @RequestBody @Valid createOrderRequestDto: CreateOrderRequestDto,
    ): ApiResponse<Long> {

        val data = orderService.createOrder(loginUser.username, createOrderRequestDto)

        return ApiResponse.success(data)
    }

    // update OrderInfo-> 개별 아이템의 수량을 바꾼다.
    // delete OrderInfo-> 개별 아이템을 삭제한다.

    // cancel Order -> order를 취소로 전환 -> 이 때 모두 출발 상태가 아니여야 한다.

    @DeleteMapping("/{orderId}/{orderItemId}")
    fun cancelOrderItem(
        @LoginUser loginUser: User,
        @PathVariable @Positive orderId: Long,
        @PathVariable @Positive orderItemId: Long,
    ): ApiResponse<Long> {
        // orderItem 취소하기
        val data = orderService.cancelOrderItem(loginUser.username, orderId, orderItemId)

        return ApiResponse.success(data)
    }

    @DeleteMapping("/{orderId}")
    fun cancelOrder(
        @LoginUser loginUser: User,
    ) {
        // orderItem 취소하기
    }

}