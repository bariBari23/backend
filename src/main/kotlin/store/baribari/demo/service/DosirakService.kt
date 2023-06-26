package store.baribari.demo.service

import org.springframework.data.domain.Pageable
import store.baribari.demo.dto.FindDosirakByQueryResponseDto


interface DosirakService {
    fun findDosirakByQuery(
        filterLiked: Boolean,
        keyword: String?,
        userEmail: String?,
        pageable: Pageable,
    ): FindDosirakByQueryResponseDto
}
