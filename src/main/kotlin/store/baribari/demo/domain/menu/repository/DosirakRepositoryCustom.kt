package store.baribari.demo.domain.menu.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import store.baribari.demo.domain.menu.entity.Dosirak
import store.baribari.demo.domain.user.entity.User

interface DosirakRepositoryCustom {
    fun customFindDosirakByQuery(
        filterLiked: Boolean,
        keyword: String?,
        user: User?,
        pageable: Pageable,
    ): Page<Dosirak>
}
