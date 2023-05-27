package store.baribari.demo.repository

import org.springframework.data.jpa.repository.JpaRepository
import store.baribari.demo.model.Store

interface StoreRepository : JpaRepository<Store, Long> {
}
