package store.baribari.demo.controller


import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import store.baribari.demo.auth.LoginUser
import store.baribari.demo.dto.common.ApiResponse
import store.baribari.demo.service.FileService

@RestController
@RequestMapping("/api/v1/file")
class FileController(
    private val fileService: FileService,
) {

    @PostMapping("/upload")
    fun test(
        @LoginUser loginUser: User,
        @RequestPart("file") file: MultipartFile,
    ): ApiResponse<String> {
        println(loginUser.username)

        val fileUrl = fileService.uploadFile(file)
        // url 리턴하기
        return ApiResponse.success(fileUrl)
    }
}
