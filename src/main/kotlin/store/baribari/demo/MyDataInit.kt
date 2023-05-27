package store.baribari.demo

import org.springframework.stereotype.Component
import store.baribari.demo.common.enums.Role
import store.baribari.demo.model.Store
import store.baribari.demo.model.User
import store.baribari.demo.model.embed.Day
import store.baribari.demo.repository.StoreRepository
import store.baribari.demo.repository.UserRepository
import java.time.DayOfWeek
import java.time.LocalTime
import javax.annotation.PostConstruct

@Component
class MyDataInit(
    private val userRepository: UserRepository,
    private val storeRepository: StoreRepository
) {

    @PostConstruct
    fun init() {
        // TODO: 배포시에는 제거하기

        val userList = userCreate()
        // dayList 넣어주기

        val dayList = dayList()

        val store = Store(
            owner = userList[1],
            name = "test store",
            phoneNumber = "010-1234-5678",
            address = "서울시 강남구 테헤란로 427",
            businessName = "테스트 가게",
            businessNumber = "123-45-67890",
            description = "테스트 가게입니다.",
            mainImage = null,
            dayList = dayList,
        )
        storeRepository.saveAndFlush(store)

    }

    private fun dayList(): List<Day> {
        val monday = Day(
            day = DayOfWeek.MONDAY,
            openTime = LocalTime.of(9, 0),
            closeTime = LocalTime.of(18, 0),
        )

        val tuesday = Day(
            day = DayOfWeek.TUESDAY,
            openTime = LocalTime.of(9, 0),
            closeTime = LocalTime.of(20, 0),
        )

        val wednesday = Day(
            day = DayOfWeek.WEDNESDAY,
            openTime = LocalTime.of(2, 0),
            closeTime = LocalTime.of(10, 0),
        )

        val thursday = Day(
            day = DayOfWeek.THURSDAY,
            openTime = LocalTime.of(2, 0),
            closeTime = LocalTime.of(10, 0),
        )

        return listOf(monday, tuesday, wednesday, thursday)
    }

    private fun userCreate(): List<User> {
        val customer = User(
            email = "customer@test.com",
            password = "customer",
            role = Role.ROLE_CUSTOMER,
        )
        val storeOwner = User(
            email = "store@test.com",
            password = "store",
            role = Role.ROLE_STORE,
        )
        userRepository.saveAll(listOf(customer, storeOwner))
        userRepository.flush()

        return listOf(customer, storeOwner)
    }
}
