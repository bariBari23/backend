package store.baribari.demo.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import store.baribari.demo.common.exception.EntityNotFoundException
import store.baribari.demo.dto.CreateOrderRequestDto
import store.baribari.demo.model.order.Order
import store.baribari.demo.model.order.OrderItem.Companion.createOrderItem
import store.baribari.demo.repository.UserRepository
import store.baribari.demo.repository.cart.CartRepository
import store.baribari.demo.repository.order.OrderRepository

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
) : OrderService {
    override fun getOrderList(
        username: String,
    ) {
        TODO()
    }

    override fun getOneOrder(
        username: String,
        orderId: Long,
    ) {
        TODO()
    }

    @Transactional
    override fun createOrder(
        username: String,
        createOrderRequestDto: CreateOrderRequestDto
    ): Long {
        val user = userRepository.findByEmailFetchCart(username)
            ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")

        val userCart = cartRepository.fetchItemListFindById(user.userCart!!.id!!)
            ?: throw EntityNotFoundException("해당 유저의 카트가 존재하지 않습니다.")

        val order = Order(
            orderItemList = mutableListOf(),
            orderDemand = createOrderRequestDto.orderDemand,
            orderPhoneNumber = createOrderRequestDto.orderPhoneNumber,
            pickUpTime = createOrderRequestDto.pickUpTime,
            payMethod = createOrderRequestDto.payMethod,
        )

        // orderItem 만들기
        val orderItems = userCart.cartItemList.map {
            createOrderItem(
                order = order,
                dosirak = it.dosirak,
                count = it.count,
                payMethod = createOrderRequestDto.payMethod
            )
        }

        //user의 order목록에 추가
        order.setCustomer(user)
        // order에 item 전부 추가와 stock 줄이기
        order.setOrderItemAndStock(orderItems)

        orderRepository.save(order)

        return order.id!!
    }

    override fun cancelOrderItem(
        username: String,
        orderId: Long,
        orderItemId: Long,
    ): Long {
        val user = userRepository.findByEmailFetchCart(username)
            ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")

        // orderId가 유저의 order인지 확인 또한 취소 상태 아닌지 확인 user를 같이 가져온다. -> fetch 써야하나?



        // orderId와 orderItem의 주인이 맞는지 확인 fetch join으로



        val userCart = cartRepository.fetchItemListFindById(user.userCart!!.id!!)
            ?: throw EntityNotFoundException("해당 유저의 카트가 존재하지 않습니다.")

        return 1
    }
}