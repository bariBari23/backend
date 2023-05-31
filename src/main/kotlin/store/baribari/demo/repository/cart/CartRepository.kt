package store.baribari.demo.repository.cart

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import store.baribari.demo.model.cart.Cart

interface CartRepository : JpaRepository<Cart, Long> {
    @Query(
        """
        SELECT c
        FROM Cart c 
        LEFT JOIN FETCH c.cartItemList
        WHERE c.id = :id
        """
    )
    fun fetchItemListFindById(id: Long): Cart?
}