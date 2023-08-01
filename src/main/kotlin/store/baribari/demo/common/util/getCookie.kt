package store.baribari.demo.common.util

import org.springframework.http.ResponseCookie
import org.springframework.util.SerializationUtils
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

fun getCookie(request: HttpServletRequest, name: String) = request.cookies?.let {
    it.find { cookie -> cookie.name == name }
}

fun addCookie(response: HttpServletResponse, name: String?, value: String?, maxAge: Long) {
    val cookie = ResponseCookie.from(name!!, value!!)
        .path("/")
        .sameSite("None")
        .httpOnly(true)
        .secure(true)
        .maxAge(maxAge.toLong())
        .build()
    response.setHeader("Set-Cookie", cookie.toString())
}

fun deleteCookie(request: HttpServletRequest, response: HttpServletResponse, name: String) {
    request.cookies?.let {
        it.find { cookie -> cookie.name == name }?.let { cookie ->
            cookie.value = ""
            cookie.path = "/"
            cookie.maxAge = 0
            response.addCookie(cookie)
        }
    }
}

fun serialize(obj: Any): String {
    return Base64.getUrlEncoder()
        .encodeToString(SerializationUtils.serialize(obj))
}

fun <T> deserialize(cookie: Cookie, cls: Class<T>): T {
    return cls.cast(
        SerializationUtils.deserialize(
            Base64.getUrlDecoder().decode(cookie.value),
        ),
    )
}