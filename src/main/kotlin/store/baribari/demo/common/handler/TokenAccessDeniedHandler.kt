package store.baribari.demo.common.handler

import com.nimbusds.jose.shaded.json.JSONObject
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import store.baribari.demo.common.enums.ErrorCode
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class TokenAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException,
    ) {
        setResponse(response)
    }

    private fun setResponse(response: HttpServletResponse) {
        response.contentType = "application/json;charset=UTF-8"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val responseJson = JSONObject()
        responseJson["status"] = "fail"
        responseJson["data"] = ErrorCode.UNAUTHORIZED.message
        response.writer.print(responseJson)
    }
}
