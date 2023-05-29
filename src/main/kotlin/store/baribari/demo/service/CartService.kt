package store.baribari.demo.service

import org.springframework.stereotype.Service
import store.baribari.demo.dto.cart.response.ClearCartResponseDto
import store.baribari.demo.dto.cart.response.DeleteCartItemResponseDto
import store.baribari.demo.dto.cart.response.AddCartItemResponseDto
import store.baribari.demo.dto.cart.request.AddItemRequestDto
import store.baribari.demo.dto.cart.response.CartInfoResponseDto
import store.baribari.demo.dto.cart.response.UpdateItemQuantityResponseDto

@Service
interface CartService {
    fun getCart(
        username: String
    ): CartInfoResponseDto

    fun addItem(
        username: String,
        request: AddItemRequestDto
    ): AddCartItemResponseDto

    fun updateItemQuantity(
        username: String,
        itemId: Long,
        quantity: Int
    ): UpdateItemQuantityResponseDto

    fun deleteItem(
        username: String,
        itemId: Long
    ): DeleteCartItemResponseDto

    // fun removeItem(request: RemoveItemRequestDto): RemoveItemResponseDto
    fun clearCart(
        username: String
    ): ClearCartResponseDto

}