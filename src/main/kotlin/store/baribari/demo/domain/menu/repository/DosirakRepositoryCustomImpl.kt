package store.baribari.demo.domain.menu.repository

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.NumberPath
import com.querydsl.core.types.dsl.StringPath
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import store.baribari.demo.common.enums.ErrorCode
import store.baribari.demo.common.exception.BizException
import store.baribari.demo.domain.menu.entity.Dosirak
import store.baribari.demo.domain.menu.entity.QBanchanDosirak.banchanDosirak
import store.baribari.demo.domain.menu.entity.QDosirak.dosirak
import store.baribari.demo.domain.store.entity.QLikeStore.likeStore
import store.baribari.demo.domain.user.entity.User

class DosirakRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory,
) : DosirakRepositoryCustom {
    override fun customFindDosirakByQuery(
        filterLiked: Boolean,
        keyword: String?,
        user: User?,
        pageable: Pageable,
    ): Page<Dosirak> {
        val likeStoreIds = returnLikStoreIds(filterLiked, user)
        val querySpecifiers: List<OrderSpecifier<*>> = sortQualifier(pageable.sort)

        // 선택된 받은 도시락
        val dosirakIds =
            queryFactory
                .select(
                    banchanDosirak.dosirak.id,
                ).from(banchanDosirak)
                .distinct()
                .leftJoin(banchanDosirak.dosirak.store)
                .leftJoin(banchanDosirak.banchan)
                .where(
                    // 찜한 가게라면 해당 가게를 필터하고 없다면 필터링 안한다.
                    idInStoreIds(banchanDosirak.dosirak.store.id, likeStoreIds, filterLiked),
                    keywordInBanchan(keyword, banchanDosirak.banchan.name),
                ).fetch()

        // 도시락에서 필요한 정보를 fetch join을 한다.
        val dosiraks =
            queryFactory
                .selectFrom(dosirak)
                .leftJoin(dosirak.store)
                .fetchJoin()
                .where(
                    dosirak.id.`in`(dosirakIds),
                ).orderBy(*querySpecifiers.toTypedArray())
                .offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
                .fetch()

        return PageImpl(
            dosiraks.toList(),
            pageable,
            dosirakIds.size.toLong(),
        )
    }

    private fun returnLikStoreIds(
        filterLiked: Boolean,
        user: User?,
    ): List<Long> =
        if (filterLiked) {
            user?.let { likeStoreIds(user) } ?: emptyList()
        } else {
            emptyList()
        }

    private fun likeStoreIds(user: User) =
        queryFactory
            .select(likeStore.store.id)
            .distinct()
            .from(likeStore)
            .leftJoin(likeStore.store)
            .leftJoin(likeStore.user)
            .where(
                usernameEq(user),
            ).fetch()

    private fun usernameEq(user: User) = likeStore.user.email.eq(user.email)

    private fun idInStoreIds(
        storeId: NumberPath<Long>,
        storeIds: List<Long>,
        filterLiked: Boolean,
    ) = if (!filterLiked) {
        null
    } else {
        storeId.`in`(storeIds)
    }

    private fun keywordInBanchan(
        keyword: String?,
        banchanName: StringPath,
    ) = if (keyword.isNullOrEmpty()) {
        null
    } else {
        banchanName.contains(keyword)
    }

    private fun sortQualifier(sort: Sort): List<OrderSpecifier<*>> =
        sort
            .map { order ->
                val path =
                    when (order.property) {
                        "price" -> dosirak.price
                        else -> throw BizException(ErrorCode.CONDITION_NOT_FULFILLED, "정렬 기준이 잘못되었습니다. ${order.property}")
                    }

                if (order.isAscending) path.asc() else path.desc()
            }.toList()
}
