package store.baribari.demo.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import store.baribari.demo.model.User
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): User?
    fun findUserByProviderId(id: String): User?
    fun findByEmailOrProviderId(email: String, id: String): User?

    @Query(
        """
        SELECT u
        FROM User u
        LEFT JOIN FETCH u.userCart
        WHERE u.email = :email
        """
    )
    fun findByEmailFetchCart(email: String): User?
}
