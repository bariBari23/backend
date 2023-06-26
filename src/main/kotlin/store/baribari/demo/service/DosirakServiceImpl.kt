package store.baribari.demo.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import store.baribari.demo.common.util.log
import store.baribari.demo.dto.FindDosirakByQueryResponseDto
import store.baribari.demo.repository.UserRepository
import store.baribari.demo.repository.menu.DosirakRepository

@Service
class DosirakServiceImpl(
    private val dosirakRepository: DosirakRepository,
    private val userRepository: UserRepository,
) : DosirakService {

    @Transactional(readOnly = true)
    override fun findDosirakByQuery(
        filterLiked: Boolean,
        keyword: String?,
        userEmail: String?,
        pageable: Pageable
    ): FindDosirakByQueryResponseDto {
        // 1. banchanlist와 store를 fetch join하면 된다.
        // 2.
        val user = userEmail?.let { userRepository.findByEmail(it) }

        log.info("keyword is: $keyword")

        val dosirakPage = dosirakRepository.findDosirakByQuery(
            filterLiked,
            keyword,
            user,
            pageable
        )

        return FindDosirakByQueryResponseDto.fromDosirakPageToDto(dosirakPage)
    }
}