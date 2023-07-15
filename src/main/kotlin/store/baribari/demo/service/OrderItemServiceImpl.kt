package store.baribari.demo.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import store.baribari.demo.common.enums.OrderStatus
import store.baribari.demo.common.exception.EntityNotFoundException
import store.baribari.demo.dto.MyOrderItemResponseDto
import store.baribari.demo.repository.UserRepository
import store.baribari.demo.repository.order.OrderItemRepository

@Service
class OrderItemServiceImpl (
    private val orderItemRepository: OrderItemRepository,
    private val userRepository: UserRepository,
) : OrderItemService{

    override fun findMyOrderItem(
        userEmail: String,
        pageable: Pageable
    ): MyOrderItemResponseDto {
        // 유저 찾기
        val user = userRepository.findByEmail(userEmail)
            ?: throw EntityNotFoundException("$userEmail 이라는 유저는 존재하지 않습니다.")

        // user에 해당하는 orderItem 찾기
        val orderItems = orderItemRepository.findByUserAndStatusFetchOrderAndUser(
            user,
            status = OrderStatus.CANCELED,
            pageable
        )

        return MyOrderItemResponseDto.fromOrderItemPageable(
            orderItems = orderItems
        )
    }
}