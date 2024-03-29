package store.baribari.demo.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import store.baribari.demo.model.LikeStore
import store.baribari.demo.model.Store
import store.baribari.demo.model.User

interface LikeStoreRepository : JpaRepository<LikeStore, Long> {
    fun findByUserAndStore(user: User, store: Store): LikeStore?

    @Query(
        """
        SELECT ls 
        FROM LikeStore ls
        LEFT JOIN FETCH ls.store s
        LEFT JOIN FETCH s.storeImageList
        WHERE ls.user = :user
        """
    )
    fun findByUserFetchStoreAndImageList(user: User): List<LikeStore>
}