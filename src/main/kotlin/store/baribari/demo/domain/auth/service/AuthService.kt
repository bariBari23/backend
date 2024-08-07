package store.baribari.demo.domain.auth.service

import store.baribari.demo.domain.auth.dto.TokenDto
import store.baribari.demo.domain.auth.dto.UserInfoDto
import store.baribari.demo.domain.auth.dto.UserLoginRequestDto
import store.baribari.demo.domain.auth.dto.UserSignUpDto
import java.util.UUID
import javax.servlet.http.HttpServletRequest

interface AuthService {
    // TODO: 회원가입
    fun saveUser(userSingUpDto: UserSignUpDto): UUID

    // TODO: 로그인
    fun loginUser(userLoginRequestDto: UserLoginRequestDto): UserInfoDto

    // TODO: 유저토큰 재생산
    fun refreshUserToken(request: HttpServletRequest): TokenDto

    // TODO: 이메일을 가진 유저가 존재하는지 확인하기
    fun getUserInfo(email: String): UserInfoDto

    // TODO: 비밀번호 변경
}
