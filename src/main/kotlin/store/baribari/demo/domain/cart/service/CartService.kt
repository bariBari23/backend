package store.baribari.demo.domain.cart.service

import org.springframework.stereotype.Service
import store.baribari.demo.domain.cart.dto.request.AddItemRequestDto
import store.baribari.demo.domain.cart.dto.response.AddCartItemResponseDto
import store.baribari.demo.domain.cart.dto.response.CartInfoResponseDto
import store.baribari.demo.domain.cart.dto.response.ClearCartResponseDto
import store.baribari.demo.domain.cart.dto.response.DeleteCartItemResponseDto
import store.baribari.demo.domain.cart.dto.response.UpdateItemQuantityResponseDto

@Service
interface CartService {
    fun getCart(username: String): CartInfoResponseDto

    fun addItem(
        username: String,
        request: AddItemRequestDto,
    ): AddCartItemResponseDto

    fun updateItemQuantity(
        username: String,
        itemId: Long,
        quantity: Int,
    ): UpdateItemQuantityResponseDto

    fun deleteItem(
        username: String,
        itemId: Long,
    ): DeleteCartItemResponseDto

    // fun removeItem(request: RemoveItemRequestDto): RemoveItemResponseDto
    fun clearCart(username: String): ClearCartResponseDto
}
