package store.baribari.demo.domain.cart.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import store.baribari.demo.common.enums.ErrorCode
import store.baribari.demo.common.exception.ConditionConflictException
import store.baribari.demo.common.exception.EntityNotFoundException
import store.baribari.demo.domain.cart.domain.Cart
import store.baribari.demo.domain.cart.domain.CartItem
import store.baribari.demo.domain.cart.dto.request.AddItemRequestDto
import store.baribari.demo.domain.cart.dto.response.AddCartItemResponseDto
import store.baribari.demo.domain.cart.dto.response.CartInfoResponseDto
import store.baribari.demo.domain.cart.dto.response.CartItemResponseDto
import store.baribari.demo.domain.cart.dto.response.ClearCartResponseDto
import store.baribari.demo.domain.cart.dto.response.DeleteCartItemResponseDto
import store.baribari.demo.domain.cart.dto.response.UpdateItemQuantityResponseDto
import store.baribari.demo.domain.cart.repository.CartItemRepository
import store.baribari.demo.domain.cart.repository.CartRepository
import store.baribari.demo.domain.menu.repository.DosirakRepository
import store.baribari.demo.domain.user.repository.UserRepository

// userCart를 사용하지 않고 cart를 사용하는 방식의 service
@Service
class NewCartServiceImpl(
    private val userRepository: UserRepository,
    private val cartRepository: CartRepository,
    private val cartItemRepository: CartItemRepository,
    private val dosirakRepository: DosirakRepository,
) : CartService {
    @Transactional(readOnly = true)
    override fun getCart(username: String): CartInfoResponseDto {
        // 유저만 가져오기
        val user =
            userRepository.findByEmail(username)
                ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")
        // itemlist를 조회할 때 fetch join으로 내부에 있는 dosiraklist의 정보를 가져온다.
        // querydsl의 시간인가?
        // 아니면 entity graph를 사용해야하나? -> 이게 답인거 같다.
        // 일단 itemlist를 가져오면서
        val cart =
            cartRepository.findByUserId(user.id!!)
                ?: throw EntityNotFoundException("해당하는 장바구니가 존재하지 않습니다.")

        val itemList = user.userCart.cartItemList

        return CartInfoResponseDto(
            cartId = user.userCart.id!!,
            items = itemList.map { CartItemResponseDto.fromCartItem(it) },
            price = itemList.sumOf { it.dosirak.price * it.count },
        )
    }

    @Transactional
    override fun addItem(
        username: String,
        request: AddItemRequestDto,
    ): AddCartItemResponseDto {
        val itemMap = request.items.associate { it.dosirakId to it.amount }

        val user =
            userRepository.findByEmailFetchCart(username)
                ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")

        val userCart =
            cartRepository.findByIdFetchItemListAndDosirak(user.userCart.id!!)
                ?: throw EntityNotFoundException("해당하는 장바구니가 존재하지 않습니다.")

        // 0으로 초기화
        initializeItemZero(itemMap, userCart)
        // 기존에 있던 아이템에 더하기
        plusItemAmount(itemMap, userCart.cartItemList)
        // 3개 넘는지 점검
        checkAmountLimit(userCart.cartItemList)

        return AddCartItemResponseDto(
            itemKindAmount = userCart.cartItemList.size,
        )
    }

    private fun checkAmountLimit(cartItemList: MutableList<CartItem>) {
        require(cartItemList.all { it.count <= MAX_CART_ITEM_AMOUNT }) {
            throw ConditionConflictException(ErrorCode.CONDITION_NOT_FULFILLED, "카트에 담긴 도시락의 개수는 한 품목당 3개를 넘을 수 없습니다.")
        }
    }

    private fun initializeItemZero(
        itemMap: Map<Long, Int>,
        cart: Cart,
    ) {
        val cartItems = cart.cartItemList
        val dosirakIdSet = cartItems.map { it.dosirak.id!! }.toSet() // 도시락 id의 목록을 나타냄
        val itemListToAdd = mutableListOf<CartItem>()

        itemMap
            .filterNot { it.key in dosirakIdSet }
            .mapNotNull { filteredMap ->
                dosirakRepository.findByIdOrNull(filteredMap.key)?.let { dosirak ->
                    itemListToAdd.add(
                        CartItem(dosirak = dosirak, cart = cart, count = 0),
                    )
                } ?: throw EntityNotFoundException("${filteredMap.key}라는 도시락이 존재하지 않습니다.")
            }

        cartItemRepository.saveAll(itemListToAdd)
        cartItems.addAll(itemListToAdd)
    }

    private fun plusItemAmount(
        itemMap: Map<Long, Int>,
        cartItems: MutableList<CartItem>,
    ) {
        cartItems
            .filter { it.dosirak.id in itemMap.keys }
            .forEach { it.count += itemMap[it.dosirak.id!!]!! }
    }

    @Transactional
    override fun updateItemQuantity(
        username: String,
        itemId: Long,
        quantity: Int,
    ): UpdateItemQuantityResponseDto {
        val user =
            userRepository.findByEmailFetchCart(username)
                ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")

        val userCart =
            cartRepository.findByIdFetchItemListAndDosirak(user.userCart.id!!)
                ?: throw EntityNotFoundException("해당하는 장바구니가 존재하지 않습니다.")

        val targetCartItem =
            userCart.cartItemList.find { it.dosirak.id == itemId }
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
        itemId: Long,
    ): DeleteCartItemResponseDto {
        val user =
            userRepository.findByEmailFetchCart(username)
                ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")

        val userCart =
            cartRepository.findByIdFetchItemListAndDosirak(user.userCart.id!!)
                ?: throw EntityNotFoundException("해당하는 장바구니가 존재하지 않습니다.")

        val cartItems = userCart.cartItemList

        val targetCartItem =
            cartItems.find { it.dosirak.id == itemId }
                ?: throw EntityNotFoundException("해당 아이템이 존재하지 않습니다.")

        cartItems.remove(targetCartItem)
        cartItemRepository.delete(targetCartItem)

        return DeleteCartItemResponseDto(
            itemId = itemId,
        )
    }

    @Transactional
    override fun clearCart(username: String): ClearCartResponseDto {
        val user =
            userRepository.findByEmailFetchCart(username)
                ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")

        val userCart =
            cartRepository.findByIdFetchItemListAndDosirak(user.userCart.id!!)
                ?: throw EntityNotFoundException("해당하는 장바구니가 존재하지 않습니다.")

        cartItemRepository.deleteAll(userCart.cartItemList)
        userCart.cartItemList.clear()

        return ClearCartResponseDto(
            user.userCart.id!!,
        )
    }
}
