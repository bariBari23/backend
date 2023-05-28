package store.baribari.demo.service

import org.springframework.stereotype.Service
import store.baribari.demo.dto.ClearCartResponseDto
import store.baribari.demo.dto.DeleteItemResPonseDto
import store.baribari.demo.dto.cart.AddCartItemResponseDto
import store.baribari.demo.dto.cart.AddItemRequestDto
import store.baribari.demo.dto.cart.CartInfoResponseDto
import store.baribari.demo.dto.cart.UpdateItemQuantityResponseDto

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
    ): DeleteItemResPonseDto

    // fun removeItem(request: RemoveItemRequestDto): RemoveItemResponseDto
    fun clearCart(
        username: String
    ): ClearCartResponseDto

}