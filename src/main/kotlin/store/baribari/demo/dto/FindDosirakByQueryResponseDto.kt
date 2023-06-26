package store.baribari.demo.dto

import org.springframework.data.domain.Page
import store.baribari.demo.model.menu.Dosirak

data class FindDosirakByQueryResponseDto(
    val dosirakList: List<DosirakDto>,
    val currentPage: Int,
    val totalPages: Int,
    val totalElements: Long,
    val numberOfElements: Int,
) {
    companion object {
        fun fromDosirakPageToDto(
            pageData: Page<Dosirak>,
        ): FindDosirakByQueryResponseDto {
            val content = pageData.content.map {
                DosirakDto.createDtoFromEntity(it)
            }
            return FindDosirakByQueryResponseDto(
                dosirakList = content,
                currentPage = pageData.pageable.pageNumber,
                totalPages = pageData.totalPages,
                totalElements = pageData.totalElements,
                numberOfElements = pageData.numberOfElements,
            )
        }
    }
}
