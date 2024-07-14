package store.baribari.demo.domain.store.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import store.baribari.demo.domain.store.entity.Store

interface StoreRepository : JpaRepository<Store, Long> {
    @EntityGraph(attributePaths = ["position"])
    override fun findAll(): List<Store>
}
