package store.baribari.demo.controller


import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import store.baribari.demo.dto.DosirakByQueryResponseDto
import store.baribari.demo.dto.FindDosirakByIdResponseDto
import store.baribari.demo.dto.common.ApiResponse
import store.baribari.demo.service.DosirakService
import java.security.Principal
import javax.validation.constraints.Positive

@Validated
@RestController
@RequestMapping("/api/v1/dosirak")
class DosirakController(
    private val dosirakService: DosirakService,
) {

    @RequestMapping("/query")
    fun findDosirakByQuery(
        @RequestParam("filterLiked") filterLiked: Boolean,
        @RequestParam("keyword", required = false) keyword: String?,
        principal: Principal?,
        pageable: Pageable,
    ): ApiResponse<DosirakByQueryResponseDto> {
        val data = dosirakService.findDosirakByQuery(
            filterLiked,
            keyword,
            principal?.name,
            pageable
        )

        return ApiResponse.success(data)
    }

    @RequestMapping("/{dosirakId}")
    fun getDosirakById(
        @PathVariable @Positive dosirakId: Long,
        principal: Principal?,
    ): ApiResponse<FindDosirakByIdResponseDto> {
        val data = dosirakService.findDosirakById(principal?.name, dosirakId)


        return ApiResponse.success(data)
    }

}