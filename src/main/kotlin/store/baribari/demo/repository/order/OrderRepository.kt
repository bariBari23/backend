package store.baribari.demo.repository.order

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import store.baribari.demo.model.User
import store.baribari.demo.model.order.Order

interface OrderRepository : JpaRepository<Order, Long> {

    @Query(
        """
        SELECT o
        FROM Order o
        LEFT JOIN FETCH o.user user
        LEFT JOIN FETCH o.orderItemList orderItem
        WHERE o.id = :orderId
        """
    )
    fun findByIdFetchUserAndOrderItem(orderId: Long): Order?

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
        """
    )
    fun findByUserFetchOrderItem(user: User, pageable: Pageable): Page<Order>
}