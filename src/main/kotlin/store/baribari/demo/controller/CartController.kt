package store.baribari.demo.controller

import org.springframework.security.core.userdetails.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import store.baribari.demo.auth.LoginUser
import store.baribari.demo.dto.cart.request.AddItemRequestDto
import store.baribari.demo.dto.cart.response.*
import store.baribari.demo.dto.common.ApiResponse
import store.baribari.demo.service.CartService
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Positive

@Validated
@RestController
@RequestMapping("/api/v1/cart")
class CartController(
    private val cartService: CartService,
) {

    // 수량은 무조건 body에 넣는거로 상정한다.

    // read
    // 카트의 정보를 읽어온다.
    @GetMapping()
    fun getCart(
        @LoginUser loginUser: User
    ): ApiResponse<CartInfoResponseDto> {
        val data = cartService.getCart(loginUser.username)
        return ApiResponse.success(data)
    }


    // update
    // 1. 카트에 상품을 담기
    //  - 신규 상품을 담는 개념이다. body에 상품 목록과 수량을 넣어준다.
    //  일단 상품이 존재하지 않는다면 수량이 0개인 객체를 생성하고
    // 마지막으로 상품마다 값을 더해준다.

    // 2. 카트에 존재하는 상품의 수량을 수정한다.
    // - url을 기반으로 진행하는게 맞다고 생각한다.
    // 0개가 들어오면 오류를 내뿜는다.

    // 3. 카트에 존재하는 상품을 삭제
    // - url을 기반으로 진행하자

    @PostMapping("")
    fun addItem(
        @LoginUser loginUser: User,
        @RequestBody @Valid addItemRequestDto: AddItemRequestDto
    ): ApiResponse<AddCartItemResponseDto> {
        // 여러개가 들어오는 경우도 가정을 하자...
        val data = cartService.addItem(loginUser.username, addItemRequestDto)
        return ApiResponse.success(data)
    }

    // 굳이 양을 표기하는 dto를 둘 필요 없이 url을 통해서 진행하자. -> rest api에 예외를 두는 방식
    @PutMapping("/{itemId}/{quantity}")
    fun updateItemQuantity(
        @LoginUser loginUser: User,
        @PathVariable @Positive itemId: Long,
        @PathVariable @Positive @Max(3) quantity: Int
    ): ApiResponse<UpdateItemQuantityResponseDto> {
        val data = cartService.updateItemQuantity(loginUser.username, itemId, quantity)
        // validated 어노테이션 붙여야 작동한다.
        return ApiResponse.success(data)
    }

    @DeleteMapping("/{itemId}")
    fun deleteItem(
        @LoginUser loginUser: User,
        @PathVariable @Positive itemId: Long
    ): ApiResponse<DeleteCartItemResponseDto> {
        // 단일 아이템으로 가정
        val data = cartService.deleteItem(loginUser.username, itemId)

        return ApiResponse.success(data)
    }

    // delete -> 카드 용량 비우기
    // 주문으로 넘길 때 수행한다. 만약에 상품의 개수가 0개라면 넘길 때 0개인거는 제외한다.
    @DeleteMapping("")
    fun emptyCart(
        @LoginUser loginUser: User
    ): ApiResponse<ClearCartResponseDto> {
        // 카트를 비운다.
        val data = cartService.clearCart(loginUser.username)

        return ApiResponse.success(data)
    }


}