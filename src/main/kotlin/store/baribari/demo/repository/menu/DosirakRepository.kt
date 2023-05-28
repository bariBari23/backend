package store.baribari.demo.repository.menu

import org.springframework.data.jpa.repository.JpaRepository
import store.baribari.demo.model.menu.Dosirak

interface DosirakRepository : JpaRepository<Dosirak, Long>