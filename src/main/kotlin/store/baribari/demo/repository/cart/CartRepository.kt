package store.baribari.demo.repository.cart

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import store.baribari.demo.model.cart.Cart

interface CartRepository : JpaRepository<Cart, Long> {
    @Query(
        """
        SELECT c
        FROM Cart c 
        LEFT JOIN FETCH c.cartItemList ci
        left JOIN FETCH ci.dosirak d
        WHERE c.id = :id
        """
    )
    fun findByIdFetchItemListAndDosirak(id: Long): Cart?
}