package store.baribari.demo.domain.cart.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import store.baribari.demo.domain.cart.domain.Cart

interface CartRepository : JpaRepository<Cart, Long> {
    @Query(
        """
        SELECT c
        FROM Cart c
        LEFT JOIN FETCH c.cartItemList ci
        left JOIN FETCH ci.dosirak d
        WHERE c.id = :id
        """,
    )
    fun findByIdFetchItemListAndDosirak(id: Long): Cart?
}
