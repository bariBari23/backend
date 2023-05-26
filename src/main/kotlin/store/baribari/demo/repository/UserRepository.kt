package store.baribari.demo.repository

import org.springframework.data.jpa.repository.JpaRepository
import store.baribari.demo.model.User
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): User?
    fun findUserByProviderId(id: String): User?
    fun findByEmailOrProviderId(email: String, id: String): User?
}
