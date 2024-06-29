package store.baribari.demo.controller

import kotlinx.coroutines.runBlocking
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import store.baribari.demo.auth.LoginUser
import store.baribari.demo.dto.common.ApiResponse
import store.baribari.demo.service.FileService

@RestController
@RequestMapping("/api/v1/file")
class FileController(
    private val fileService: FileService,
) {

    @GetMapping("/presign")
    fun preSignUrl(
        @LoginUser loginUser: User,
    ): ApiResponse<String> {
        return runBlocking {
            val preSingedUrl = fileService.geneatePreSingedUrl(loginUser.username)
            ApiResponse.success(preSingedUrl)
        }
    }
}
