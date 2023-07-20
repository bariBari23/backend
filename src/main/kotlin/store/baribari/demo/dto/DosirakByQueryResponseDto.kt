package store.baribari.demo.dto

import org.springframework.data.domain.Page
import store.baribari.demo.model.User
import store.baribari.demo.model.menu.Dosirak

data class DosirakByQueryResponseDto(
    val dosirakList: List<DosirakByQueryResponseElementDto>,
    val currentPage: Int,
    val totalPages: Int,
    val totalElements: Long,
    val numberOfElements: Int,
) {
    companion object {
        fun fromDosirakPageToDto(
            pageData: Page<Dosirak>,
            user: User?,
        ): DosirakByQueryResponseDto {
            val content = pageData.content.map {
                DosirakByQueryResponseElementDto.createDtoFromEntity(it, user)
            }
            return DosirakByQueryResponseDto(
                dosirakList = content,
                currentPage = pageData.pageable.pageNumber,
                totalPages = pageData.totalPages,
                totalElements = pageData.totalElements,
                numberOfElements = pageData.numberOfElements,
            )
        }
    }
}
