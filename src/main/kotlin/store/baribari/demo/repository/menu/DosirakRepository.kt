package store.baribari.demo.repository.menu

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import store.baribari.demo.model.menu.Dosirak

interface DosirakRepository : JpaRepository<Dosirak, Long>, DosirakRepositoryCustom {

    @Query(
        """
            SELECT d
            FROM Dosirak d
            LEFT JOIN FETCH d.store s
            LEFT JOIN FETCH d.dosirakBanchanList b
            LEFT JOIN FETCH b.banchan
            WHERE d.id = :dosirakId
        """
    )
    fun findByIdFetchStoreAndBanchanList(dosirakId: Long): Dosirak
}
