package store.baribari.demo.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import store.baribari.demo.model.Review
import store.baribari.demo.model.Store

interface ReviewRepository : JpaRepository<Review, Long> {
    @Query(
        """
        SELECT r 
        FROM Review r 
        JOIN FETCH r.orderItem oi
        JOIN FETCH r.writer 
        WHERE r.id = :reviewId
        """
    )
    fun findByIdFetchOrderItemAndWriter(reviewId: Long): Review?

    @Query(value = "SELECT r FROM Review r JOIN FETCH r.orderItem oi JOIN FETCH oi.order o ORDER BY r.id")
    fun findByStoreFetchOrderItemAndDosirak(store: Store): List<Review>?
}
