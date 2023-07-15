package store.baribari.demo.controller

import org.springframework.data.domain.Pageable
import org.springframework.security.core.userdetails.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import store.baribari.demo.auth.LoginUser
import store.baribari.demo.dto.MyOrderItemResponseDto
import store.baribari.demo.dto.common.ApiResponse
import store.baribari.demo.service.OrderItemService

@Validated
@RestController
@RequestMapping("/api/v1/orderItem")
class OrderItemController(
    private val orderItemService: OrderItemService,
) {

    @GetMapping("")
    fun findMyOrderItem(
        @LoginUser loginUser: User,
        pageable: Pageable,
    ): ApiResponse<MyOrderItemResponseDto> {
        val data = orderItemService.findMyOrderItem(loginUser.username, pageable)

        return ApiResponse.success(data)
    }


}