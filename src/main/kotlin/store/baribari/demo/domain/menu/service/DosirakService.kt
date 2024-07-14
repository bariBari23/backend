package store.baribari.demo.domain.menu.service

import org.springframework.data.domain.Pageable
import store.baribari.demo.domain.menu.dto.DosirakByQueryResponseDto
import store.baribari.demo.domain.menu.dto.FindDosirakByIdResponseDto

interface DosirakService {
    fun findDosirakByQuery(
        filterLiked: Boolean,
        keyword: String?,
        userEmail: String?,
        pageable: Pageable,
    ): DosirakByQueryResponseDto

    fun findDosirakById(
        userEmail: String?,
        dosirakId: Long,
    ): FindDosirakByIdResponseDto
}
