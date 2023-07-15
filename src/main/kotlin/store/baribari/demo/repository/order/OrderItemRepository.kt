package store.baribari.demo.repository.order

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import store.baribari.demo.common.enums.OrderStatus
import store.baribari.demo.model.User
import store.baribari.demo.model.order.OrderItem

interface OrderItemRepository : JpaRepository<OrderItem, Long> {


    @Query(
        """
        SELECT oi 
        FROM OrderItem oi
        LEFT JOIN FETCH oi.order o
        LEFT JOIN FETCH o.user u
        LEFT JOIN FETCH oi.dosirak d
        LEFT JOIN FETCH d.store s
        WHERE u = :user AND oi.status <> :status
        ORDER BY oi.id DESC
        """,
        countQuery =
        """
                SELECT oi.id
                FROM OrderItem oi
                WHERE oi.order.user = :user AND oi.status <> :status
           """
    )
    fun findByUserAndStatusFetchOrderAndUser(
        user: User,
        status: OrderStatus,
        pageable: Pageable
    ): Page<OrderItem>


    @Query(
        """
        SELECT oi 
        FROM OrderItem oi
        LEFT JOIN FETCH oi.order o
        LEFT JOIN FETCH o.user u
        WHERE oi.id = :orderItemId
        """
    )
    fun findByOrderItemIdFetchOrderAndUser(orderItemId: Long): OrderItem?
}