package store.baribari.demo.domain.order.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import store.baribari.demo.domain.order.entity.Order
import store.baribari.demo.domain.user.entity.User

interface OrderRepository : JpaRepository<Order, Long> {
    @Query(
        """
        SELECT o
        FROM Order o
        LEFT JOIN FETCH o.user user
        LEFT JOIN FETCH o.orderItemList orderItem
        LEFT JOIN FETCH orderItem.dosirak dosirak
        WHERE o.id = :orderId
        """,
    )
    fun findByIdFetchUserAndOrderItemAndDosirak(orderId: Long): Order?

    @Query(
        """
        SELECT DISTINCT o
        FROM Order o
        LEFT JOIN FETCH o.orderItemList orderItem
        WHERE o.user = :user
        """,
        countQuery = """
        SELECT COUNT(DISTINCT o)
        FROM Order o
        WHERE o.user = :user
        """,
    )
    fun findByUserFetchOrderItem(
        user: User,
        pageable: Pageable,
    ): Page<Order>
}
