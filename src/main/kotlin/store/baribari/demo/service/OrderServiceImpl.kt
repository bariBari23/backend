package store.baribari.demo.service

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import store.baribari.demo.common.enums.OrderStatus
import store.baribari.demo.common.exception.EntityNotFoundException
import store.baribari.demo.dto.order.request.CancelOrderItemResponseDto
import store.baribari.demo.dto.order.request.CreateOrderRequestDto
import store.baribari.demo.dto.order.response.FindAllOrderResponseDto
import store.baribari.demo.dto.order.response.FindOneOrderResponseDto
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
    override fun findMyOrder(
        userEmail: String,
        pageable: Pageable,
    ): FindAllOrderResponseDto {
        val user = userRepository.findByEmail(userEmail)
            ?: throw EntityNotFoundException("$userEmail 이라는 유저는 존재하지 않습니다.")

        val orders = orderRepository.findByUserFetchOrderItem(user, pageable)

        // TODO: 일단 취소한 orderItem도 표시하는거로 설정
        return FindAllOrderResponseDto(
            orderList = orders.toList().map { FindOneOrderResponseDto.fromOrder(it) },
            pageable = pageable,
        )
    }

    @Transactional(readOnly = true)
    override fun findOneOrder(
        userEmail: String,
        orderId: Long,
    ): FindOneOrderResponseDto {
        val user = userRepository.findByEmail(userEmail)
            ?: throw EntityNotFoundException("$userEmail 이라는 유저는 존재하지 않습니다.")

        val order = orderRepository.findByIdFetchUserAndOrderItemAndDosirak(orderId)
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
        userEmail: String,
        createOrderRequestDto: CreateOrderRequestDto
    ): Long {
        val user = userRepository.findByEmailFetchCart(userEmail)
            ?: throw EntityNotFoundException("$userEmail 이라는 유저는 존재하지 않습니다.")

        val userCart = cartRepository.findByIdFetchItemListAndDosirak(user.userCart.id!!)
            ?: throw EntityNotFoundException("해당 유저의 카트가 존재하지 않습니다.")

        val order = Order(
            orderItemList = mutableListOf(),
            orderDemand = createOrderRequestDto.orderDemand,
            orderPhoneNumber = createOrderRequestDto.orderPhoneNumber,
            pickUpTime = createOrderRequestDto.pickUpTime,
            payMethod = createOrderRequestDto.payMethod,
        )

        require(userCart.cartItemList.isNotEmpty()) { "해당 유저의 카트에 아무것도 없습니다." }

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
        userEmail: String,
        orderId: Long,
        orderItemId: Long,
    ): CancelOrderItemResponseDto {
        val user = userRepository.findByEmail(userEmail)
            ?: throw EntityNotFoundException("$userEmail 이라는 유저는 존재하지 않습니다.")

        // orderId가 유저의 order인지 확인 또한 취소 상태 아닌지 확인 user를 같이 가져온다. -> fetch 써야하나?
        // fetch로 orderItem 가져온다. -> 거기에서 찾자
        val order = orderRepository.findByIdFetchUserAndOrderItemAndDosirak(orderId)
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
        userEmail: String,
        orderId: Long
    ): Long {
        val user = userRepository.findByEmail(userEmail)
            ?: throw EntityNotFoundException("$userEmail 이라는 유저는 존재하지 않습니다.")

        val order = orderRepository.findByIdFetchUserAndOrderItemAndDosirak(orderId)
            ?: throw EntityNotFoundException("해당 주문이 존재하지 않습니다.")

        if (user.id != order.user!!.id)
            throw EntityNotFoundException("해당 주문의 주인이 아닙니다.")

        order.cancel()

        return order.id!!
    }

    @Transactional
    override fun orderedOrder(
        orderId: Long
    ): FindOneOrderResponseDto {
        // TODO: 여기 아래부터는 상점이나 관리자만 할 수 있도록
        
        val order = orderRepository.findByIdFetchUserAndOrderItemAndDosirak(orderId)
            ?: throw EntityNotFoundException("해당 주문이 존재하지 않습니다.")

        require(order.status == OrderStatus.READY) { "주문 상태가 준비중이 아닙니다." }

        order.ordered()

        return FindOneOrderResponseDto.fromOrder(order)
    }

    override fun orderedOrderItem(
        orderItemId: Long
    ): OrderItemDto {
        // TODO: 여기 아래부터는 상점이나 관리자만 할 수 있도록
        val orderItem = orderItemRepository.findByIdOrNull(orderItemId)
            ?: throw EntityNotFoundException("해당 주문 아이템이 존재하지 않습니다.")

        orderItem.ordered()

        return OrderItemDto.fromOrderItem(orderItem)
    }

    @Transactional
    override fun completeOrder(
        orderId: Long
    ): FindOneOrderResponseDto {
        val order = orderRepository.findByIdFetchUserAndOrderItemAndDosirak(orderId)
            ?: throw EntityNotFoundException("해당 주문이 존재하지 않습니다.")

        require(order.status == OrderStatus.ORDERED) { "주문된 항목만 준비완료 상태로 전환 가능 수 있습니다." }

        order.complete()

        return FindOneOrderResponseDto.fromOrder(order)
    }

    @Transactional
    override fun completeOrderItem(
        orderItem: Long
    ): OrderItemDto {
        val orderItems = orderItemRepository.findByIdOrNull(orderItem)
            ?: throw EntityNotFoundException("해당 주문 아이템이 존재하지 않습니다.")

        orderItems.complete()

        return OrderItemDto.fromOrderItem(orderItems)
    }

    @Transactional
    override fun forcePickupOrder(
        orderId: Long
    ): Long {
        val order = orderRepository.findByIdFetchUserAndOrderItemAndDosirak(orderId)
            ?: throw EntityNotFoundException("해당 주문이 존재하지 않습니다.")

        order.forcePickUp()

        return order.id!!
    }

    @Transactional
    override fun forcePickUpOrderItem(
        orderItemId: Long
    ): Long {
        val orderItem = orderItemRepository.findByIdOrNull(orderItemId)
            ?: throw EntityNotFoundException("해당 주문 아이템이 존재하지 않습니다.")

        orderItem.pickup()

        return orderItem.id!!
    }
}