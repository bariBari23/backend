package store.baribari.demo.repository.menu

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import store.baribari.demo.model.User
import store.baribari.demo.model.menu.Dosirak

interface DosirakRepositoryCustom {
    fun customFindDosirakByQuery(
        filterLiked: Boolean,
        keyword: String?,
        user: User?,
        pageable: Pageable,
    ): Page<Dosirak>
}
