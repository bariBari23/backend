package store.baribari.demo.service

import org.springframework.data.domain.Pageable
import store.baribari.demo.dto.FindDosirakByIdResponseDto
import store.baribari.demo.dto.DosirakByQueryResponseDto


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
