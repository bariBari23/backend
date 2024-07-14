package store.baribari.demo.domain.review.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import store.baribari.demo.domain.review.entity.Review
import store.baribari.demo.domain.store.entity.Store

interface ReviewRepository : JpaRepository<Review, Long> {
    @Query(
        """
            SELECT r
            FROM Review r
            LEFT JOIN FETCH r.orderItem oi
            LEFT JOIN FETCH r.writer
            LEFT JOIN FETCH oi.order o
            WHERE r.id = :reviewId
            """,
    )
    fun findByIdFetchOrderItemAndWriterAndOrder(reviewId: Long): Review?

    @Query(
        value = """
        SELECT r
        FROM Review r
        LEFT JOIN FETCH r.orderItem oi
        LEFT JOIN FETCH oi.order o
        LEFT JOIN FETCH oi.dosirak d
        LEFT JOIN FETCH d.store s
        WHERE s = :store
        ORDER BY r.id DESC
        """,
    )
    fun findByStoreFetchOrderItemAndDosirakAndOrder(store: Store): List<Review>

    @Query(
        value = """
            SELECT r
            FROM Review r
            LEFT JOIN FETCH r.orderItem oi
            LEFT JOIN FETCH oi.dosirak d
            LEFT JOIN FETCH d.store s
            WHERE d.store = :store
            """,
    )
    fun findAllByStore(store: Store): List<Review>
}
