package store.baribari.demo.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import store.baribari.demo.dto.FindDosirakByIdResponseDto
import store.baribari.demo.dto.DosirakByQueryResponseDto
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
    ): DosirakByQueryResponseDto {
        // 1. banchanlist와 store를 fetch join하면 된다.
        // 2.
        val user = userEmail?.let { userRepository.findByEmail(it) }

        //log.info("keyword is: $keyword")

        val dosirakPage = dosirakRepository.customFindDosirakByQuery(
            filterLiked,
            keyword,
            user,
            pageable
        )

        return DosirakByQueryResponseDto.fromDosirakPageToDto(dosirakPage)
    }

    override fun findDosirakById(
        userEmail: String?,
        dosirakId: Long
    ): FindDosirakByIdResponseDto {
        val user = userEmail?.let { userRepository.findByEmailFetchStoreLikeList(it) }

        // 1. dosirak을 가져온다. banchaList store와 함께
        // 2. dosirak을 photoList를 가져온다.

        val dosirak = dosirakRepository.findByIdFetchStoreAndBanchanList(dosirakId)


        return FindDosirakByIdResponseDto.fromDosirakToDto(dosirak, user)
    }
}