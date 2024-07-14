package store.baribari.demo.domain.store.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import store.baribari.demo.domain.store.entity.LikeStore
import store.baribari.demo.domain.store.entity.Store
import store.baribari.demo.domain.user.entity.User

interface LikeStoreRepository : JpaRepository<LikeStore, Long> {
    fun findByUserAndStore(
        user: User,
        store: Store,
    ): LikeStore?

    @Query(
        """
        SELECT ls
        FROM LikeStore ls
        LEFT JOIN FETCH ls.store s
        LEFT JOIN FETCH s.storeImageList
        WHERE ls.user = :user
        """,
    )
    fun findByUserFetchStoreAndImageList(user: User): List<LikeStore>
}
