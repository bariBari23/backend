package store.baribari.demo.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import store.baribari.demo.model.Review

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

    @Query(
        """
        SELECT r 
        FROM Review r 
        JOIN FETCH r.orderItem oi
        JOIN FETCH oi.order o
        JOIN FETCH o.user        
        JOIN FETCH oi.dosirak d
        JOIN FETCH d.store s
        WHERE s.id = :storeId
        """
    )
    fun findByStoreFetchOrderItemAndDosirak(storeId: Long): List<Review>?
}
