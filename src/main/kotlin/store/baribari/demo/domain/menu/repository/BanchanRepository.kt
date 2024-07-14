package store.baribari.demo.domain.menu.repository

import org.springframework.data.jpa.repository.JpaRepository
import store.baribari.demo.domain.menu.entity.Banchan

interface BanchanRepository : JpaRepository<Banchan, Long>
