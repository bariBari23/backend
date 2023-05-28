package store.baribari.demo.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import store.baribari.demo.common.exception.EntityNotFoundException
import store.baribari.demo.common.util.log
import store.baribari.demo.dto.ClearCartResponseDto
import store.baribari.demo.dto.DeleteItemResPonseDto
import store.baribari.demo.dto.cart.*
import store.baribari.demo.model.User
import store.baribari.demo.model.cart.Cart
import store.baribari.demo.model.cart.CartItem
import store.baribari.demo.repository.UserRepository
import store.baribari.demo.repository.cart.CartItemRepository
import store.baribari.demo.repository.cart.CartRepository
import store.baribari.demo.repository.menu.DosirakRepository
import javax.validation.constraints.Positive

@Service
class CartServiceImpl(
    private val cartRepository: CartRepository,
    private val cartItemRepository: CartItemRepository,
    private val userRepository: UserRepository,
    private val dosirakRepository: DosirakRepository,
) : CartService {

    @Transactional(readOnly = true)
    override fun getCart(username: String): CartInfoResponseDto {
        val user = userRepository.findByEmail(username)
            ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")

        val itmeList = user.userCart.cartItemList

        return CartInfoResponseDto(
            cartId = user.userCart.id!!,
            items = itmeList.map { CartItemResponseDto.fromCartItem(it) }
        )
    }

    @Transactional
    override fun addItem(
        username: String,
        request: AddItemRequestDto
    ): AddCartItemResponseDto {
        val user = userRepository.findByEmail(username)
            ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")

        log.info("itemMap: ${request.itemMap}")

        // 0으로 초기화
        initializeItemZero(request.itemMap, user)

        // 기존에 있던 아이템에 더하기
        plusItemAmount(request.itemMap, user.userCart)


        return AddCartItemResponseDto(
            itemKindAmount = user.userCart.cartItemList.size
        )
    }

    private fun initializeItemZero(
        itemMap: Map<Long, @Positive Int>,
        user: User,
    ) {
        val cart = user.userCart
        val dosirakIdSet = cart.cartItemList.map { it.dosirak.id!! }.toSet() // 도시락 id의 목록을 나타냄

        for (item in itemMap) {
            if (item.key !in dosirakIdSet) {
                val dosirak = dosirakRepository.findByIdOrNull(item.key)
                    ?: throw EntityNotFoundException("도시락이 존재하지 않습니다.")

                val newCartItem = CartItem(
                    dosirak = dosirak,
                    cart = user.userCart,
                    count = 0
                )
                cartItemRepository.save(newCartItem)
                cart.cartItemList.add(newCartItem)
            }
        }
    }

    private fun plusItemAmount(
        itemMap: Map<Long, Int>,
        cart: Cart,
    ) {
        for (item in cart.cartItemList)
            if (item.dosirak.id!! in itemMap.keys) {

                if (itemMap[item.dosirak.id!!]!! <= 0)
                    throw IllegalArgumentException("양수만 가능합니다.")

                item.count += itemMap[item.dosirak.id!!]!!
            }

    }

    @Transactional
    override fun updateItemQuantity(
        username: String,
        itemId: Long,
        quantity: Int
    ): UpdateItemQuantityResponseDto {

        val user = userRepository.findByEmail(username)
            ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")

        val cart = user.userCart
        val targetCartItem = cart.cartItemList.find { it.dosirak.id == itemId }
            ?: throw EntityNotFoundException("해당 아이템이 존재하지 않습니다.")

        targetCartItem.count = quantity

        return UpdateItemQuantityResponseDto(
            itemId = itemId,
            quantity = quantity,
        )
    }

    @Transactional
    override fun deleteItem(
        username: String,
        itemId: Long
    ): DeleteItemResPonseDto {
        val user = userRepository.findByEmail(username)
            ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")

        val cart = user.userCart
        val targetCartItem = cart.cartItemList.find { it.dosirak.id == itemId }
            ?: throw EntityNotFoundException("해당 아이템이 존재하지 않습니다.")

        cart.cartItemList.remove(targetCartItem)
        cartItemRepository.delete(targetCartItem)

        return DeleteItemResPonseDto(
            itemId = itemId,
        )
    }

    @Transactional
    override fun clearCart(
        username: String
    ): ClearCartResponseDto {
        val user = userRepository.findByEmail(username)
            ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")

        val cart = user.userCart

        for (item in cart.cartItemList)
            cartItemRepository.delete(item)

        cart.cartItemList.clear()

        return ClearCartResponseDto(
            user.userCart.id!!
        )
    }
}