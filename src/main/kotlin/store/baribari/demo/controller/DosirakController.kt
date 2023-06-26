package store.baribari.demo.controller


import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import store.baribari.demo.dto.FindDosirakByQueryResponseDto
import store.baribari.demo.dto.common.ApiResponse
import store.baribari.demo.service.DosirakService
import java.security.Principal

@Validated
@RestController
@RequestMapping("/api/v1/dosirak")
class DosirakController(
    private val dosirakService: DosirakService,
) {

    @RequestMapping("/query")
    fun getDosirak(
        @RequestParam("filterLiked") filterLiked: Boolean,
        @RequestParam("keyword", required = false) keyword: String?,
        principal: Principal?,
        pageable: Pageable,
    ): ApiResponse<FindDosirakByQueryResponseDto> {
        val data = dosirakService.findDosirakByQuery(
            filterLiked,
            keyword,
            principal?.name,
            pageable
        )

        return ApiResponse.success(data)
    }

}