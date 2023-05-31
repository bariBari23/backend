package store.baribari.demo.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import store.baribari.demo.common.exception.EntityNotFoundException
import store.baribari.demo.dto.order.request.CancelOrderItemResponseDto
import store.baribari.demo.dto.order.request.CreateOrderRequestDto
import store.baribari.demo.dto.order.response.FindOneOrderResponseDto
import store.baribari.demo.dto.order.response.FindAllOrderResponseDto
import store.baribari.demo.dto.order.response.OrderItemDto
import store.baribari.demo.model.order.Order
import store.baribari.demo.model.order.OrderItem.Companion.createOrderItem
import store.baribari.demo.repository.UserRepository
import store.baribari.demo.repository.cart.CartRepository
import store.baribari.demo.repository.order.OrderItemRepository
import store.baribari.demo.repository.order.OrderRepository

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val orderItemRepository: OrderItemRepository,
) : OrderService {

    @Transactional(readOnly = true)
    override fun findAllOrder(
        username: String
    ): FindAllOrderResponseDto {
        val user = userRepository.findByEmail(username)
            ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")

        val orders = orderRepository.findByUserFetchOrderItem(user)

        // TODO: 일단 취소한 orderItem도 표시하는거로 설정
        return FindAllOrderResponseDto(
            orderCount = orders.size.toLong(),
            orderList = orders.map { FindOneOrderResponseDto.fromOrder(it) },
        )
    }

    @Transactional(readOnly = true)
    override fun findOneOrder(
        username: String,
        orderId: Long,
    ): FindOneOrderResponseDto {
        // TODO: 도시락 찾는 쿼리 하나더 줄일 수 있긴함... graph 나중에 써보자 
        val user = userRepository.findByEmail(username)
            ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")

        val order = orderRepository.findByIdFetchUserAndOrderItem(orderId)
            ?: throw EntityNotFoundException("해당 주문이 존재하지 않습니다.")

        if (user.id != order.user!!.id)
            throw EntityNotFoundException("해당 주문의 주인이 아닙니다.")

        // TODO: 일단 취소한 orderItem도 표시하는거로 설정
        return FindOneOrderResponseDto(
            orderId = order.id!!,
            orderItemList = order.orderItemList.map { OrderItemDto.fromOrderItem(it) },
            price = order.price,
            status = order.status,
        )
    }

    @Transactional
    override fun createOrder(
        username: String,
        createOrderRequestDto: CreateOrderRequestDto
    ): Long {
        val user = userRepository.findByEmailFetchCart(username)
            ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")

        val userCart = cartRepository.fetchItemListFindById(user.userCart.id!!)
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

    @Transactional
    override fun cancelOrderItem(
        username: String,
        orderId: Long,
        orderItemId: Long,
    ): CancelOrderItemResponseDto {
        // TODO: 여기도 도시락 쿼리 하나 더 줄이는게 가능하긴함 나중에 해보자!
        val user = userRepository.findByEmail(username)
            ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")

        // orderId가 유저의 order인지 확인 또한 취소 상태 아닌지 확인 user를 같이 가져온다. -> fetch 써야하나?
        // fetch로 orderItem 가져온다. -> 거기에서 찾자
        val order = orderRepository.findByIdFetchUserAndOrderItem(orderId)
            ?: throw EntityNotFoundException("해당 주문이 존재하지 않습니다.")

        val orderItem = order.orderItemList.find { it.id == orderItemId }
            ?: throw EntityNotFoundException("해당 주문 아이템이 존재하지 않습니다.")

        // 로그인 user 와 order의 주인이 같은지 확인
        if (user.id != order.user!!.id)
            throw EntityNotFoundException("해당 주문의 주인이 아닙니다.")


        orderItem.cancel() // 취소 처리


        return CancelOrderItemResponseDto(
            orderItemId = orderItem.id!!,
            price = orderItem.price,
        )
    }

    @Transactional
    override fun cancelOrder(
        username: String,
        orderId: Long
    ): Long {
        val user = userRepository.findByEmail(username)
            ?: throw EntityNotFoundException("$username 이라는 유저는 존재하지 않습니다.")

        // orderId가 유저의 order인지 확인 또한 취소 상태 아닌지 확인 user를 같이 가져온다. -> fetch 써야하나?
        // fetch로 orderItem 가져온다. -> 거기에서 찾자
        val order = orderRepository.findByIdFetchUserAndOrderItem(orderId)
            ?: throw EntityNotFoundException("해당 주문이 존재하지 않습니다.")

        if (user.id != order.user!!.id)
            throw EntityNotFoundException("해당 주문의 주인이 아닙니다.")

        order.cancel()

        return order.id!!
    }
}