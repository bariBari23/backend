package store.baribari.demo.common

import kotlinx.coroutines.runBlocking
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import store.baribari.demo.common.dto.ApiResponse
import store.baribari.demo.common.service.FileService
import store.baribari.demo.domain.auth.LoginUser

@RestController
@RequestMapping("/api/v1/file")
class FileController(
    private val fileService: FileService,
) {
    @GetMapping("/presign")
    fun preSignUrl(
        @LoginUser loginUser: User,
    ): ApiResponse<String> =
        runBlocking {
            val preSingedUrl = fileService.geneatePreSingedUrl(loginUser.username)
            ApiResponse.success(preSingedUrl)
        }
}
