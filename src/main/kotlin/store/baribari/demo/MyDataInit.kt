package store.baribari.demo

import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import store.baribari.demo.common.entity.embed.Day
import store.baribari.demo.common.entity.embed.Position
import store.baribari.demo.common.enums.Role
import store.baribari.demo.domain.cart.domain.Cart
import store.baribari.demo.domain.cart.repository.CartRepository
import store.baribari.demo.domain.menu.entity.Banchan
import store.baribari.demo.domain.menu.entity.BanchanDosirak
import store.baribari.demo.domain.menu.entity.Dosirak
import store.baribari.demo.domain.menu.repository.BanchanDosirakRepository
import store.baribari.demo.domain.menu.repository.BanchanRepository
import store.baribari.demo.domain.menu.repository.DosirakRepository
import store.baribari.demo.domain.store.entity.Store
import store.baribari.demo.domain.store.repository.StoreRepository
import store.baribari.demo.domain.user.entity.User
import store.baribari.demo.domain.user.repository.UserRepository
import java.time.DayOfWeek
import java.time.LocalTime
import javax.annotation.PostConstruct
import kotlin.random.Random.Default.nextInt

@Profile("local")
@Component
class MyDataInit(
    private val userRepository: UserRepository,
    private val storeRepository: StoreRepository,
    private val cartRepository: CartRepository,
    private val banchanRepository: BanchanRepository,
    private val dosirakRepository: DosirakRepository,
    private val banchanDosirakRepository: BanchanDosirakRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
) {
    @PostConstruct
    fun init() {
        // TODO: 배포시에는 제거하기

        val userList = userCreate()
        // dayList 넣어주기

        val dayList = dayList()

        val store =
            Store(
                owner = userList[1],
                name = "test store",
                phoneNumber = "010-1234-5678",
                address = "서울시 강남구 테헤란로 427",
                businessName = "테스트 가게",
                businessNumber = "123-45-67890",
                description = "테스트 가게입니다.",
                position =
                Position(
                    latitude = 37.4912411,
                    longitude = 127.065716,
                ),
                // dayList = dayList,
            )
        // 37.4912411,127.065716/37.4927015,127.0615472

        val store2 =
            Store(
                owner = userList[1],
                name = "test store2",
                phoneNumber = "010-1234-5678222",
                address = "서울시 강남구 테헤란로 427",
                businessName = "테스트 가게 2",
                businessNumber = "123-45-67890",
                description = "테스트 가게입니다.",
                // dayList = dayList,
            )

        storeRepository.saveAllAndFlush(listOf(store, store2))
        val banchanList = banchanListMaker(store)
        val dosirakList = dosirakListMaker(store)
        banchanDosirakMatchMaker(banchanList, dosirakList)
    }

    private fun banchanDosirakMatchMaker(
        banchanList: List<Banchan>,
        dosirakList: List<Dosirak>,
    ) {
        val banchanDosirakList = mutableListOf<BanchanDosirak>()
        for (i in dosirakList.indices) {
            for (j in 0..2) {
                banchanDosirakList.add(
                    BanchanDosirak(
                        banchan = banchanList[i * 3 + j],
                        dosirak = dosirakList[i],
                    ),
                )
            }
        }
        banchanDosirakRepository.saveAll(banchanDosirakList)
    }

    private fun banchanListMaker(store: Store): List<Banchan> {
        val ret = mutableListOf<Banchan>()

        for (i in 1..10) {
            ret.add(
                Banchan(
                    name = "테스트 반찬 $i",
                    description = "테스트 반찬입니다. $i",
                    gram = 100,
                    store = store,
                ),
            )
        }

        for (i in 11..20) {
            ret.add(
                Banchan(
                    name = "유미네 반찬 $i",
                    description = "테스트 반찬입니다. $i",
                    gram = 100,
                    store = store,
                ),
            )
        }

        for (i in 21..30) {
            ret.add(
                Banchan(
                    name = "현우네 반찬 $i",
                    description = "테스트 반찬입니다. $i",
                    gram = 100,
                    store = store,
                ),
            )
        }

        // 랜덤으로 돌려서 검색 성능 확인 중

        for (i in 1..2) {
            val idx = nextInt(29) + 1
            ret[idx].name = "나명이네 음식 ${idx + 1}"
        }

        for (i in 1..2) {
            val idx = nextInt(29) + 1
            ret[idx].name = "도규네 집밥 ${idx + 1}"
        }

        for (i in 1..2) {
            val idx = nextInt(29) + 1
            ret[idx].name = "민주네 음식 ${idx + 1}"
        }

        for (i in 1..2) {
            val idx = nextInt(29) + 1
            ret[idx].name = "예린이 영양간식 ${idx + 1}"
        }

        for (i in 1..2) {
            val idx = nextInt(29) + 1
            ret[idx].name = "지은이네 술안주 ${idx + 1}"
        }

        banchanRepository.saveAll(ret)
        return ret
    }

    private fun dayList(): MutableList<Day> {
        val monday =
            Day(
                day = DayOfWeek.MONDAY,
                openTime = LocalTime.of(9, 0),
                closeTime = LocalTime.of(18, 0),
            )

        val tuesday =
            Day(
                day = DayOfWeek.TUESDAY,
                openTime = LocalTime.of(9, 0),
                closeTime = LocalTime.of(20, 0),
            )

        val wednesday =
            Day(
                day = DayOfWeek.WEDNESDAY,
                openTime = LocalTime.of(2, 0),
                closeTime = LocalTime.of(10, 0),
            )

        val thursday =
            Day(
                day = DayOfWeek.THURSDAY,
                openTime = LocalTime.of(2, 0),
                closeTime = LocalTime.of(10, 0),
            )

        return mutableListOf(monday, tuesday, wednesday, thursday)
    }

    private fun userCreate(): List<User> {
        // 37.4927015,127.0615472 400미터 예상
        val customerCart = Cart()
        val storeCart = Cart()
        // 1차로 flush
        cartRepository.saveAllAndFlush(listOf(customerCart, storeCart))

        val customer =
            User(
                email = "customer@test.com",
                password = "customer",
                role = Role.ROLE_CUSTOMER,
                userCart = customerCart,
                phoneNumber = "010-1234-5678",
                nickname = "testuser",
                position =
                Position(
                    latitude = 37.4927015,
                    longitude = 127.0615472,
                ),
            )
        val encodedPassword1 = passwordEncoder.encode(customer.password)
        customer.encodePassword(encodedPassword1)

        val storeOwner =
            User(
                email = "store@test.com",
                password = "store",
                role = Role.ROLE_STORE,
                userCart = storeCart,
                phoneNumber = "010-9876-5432",
                nickname = "testuser2",
            )
        val encodedPassword2 = passwordEncoder.encode(storeOwner.password)
        storeOwner.encodePassword(encodedPassword2)

        val newCustomer = userRepository.saveAndFlush(customer)
        val newStoreOwner = userRepository.saveAndFlush(storeOwner)

        // 2차로 save
        customerCart.userId = newCustomer.id
        storeCart.userId = newStoreOwner.id
        cartRepository.saveAll(listOf(customerCart, storeCart))

        return listOf(customer, storeOwner)
    }

    private fun dosirakListMaker(store: Store): List<Dosirak> {
        val tempList = mutableListOf<Dosirak>()

        for (i in 1..10) {
            val temp =
                Dosirak(
                    name = "테스트 도시락 $i",
                    description = "테스트 도시락입니다. $i",
                    store = store,
                    price = (1..10).random() * 100,
                    mealTimes = (1..3).random(),
                )
            temp.changeStock(100)

            tempList.add(temp)
        }

        dosirakRepository.saveAll(tempList)
        dosirakRepository.flush()
        return tempList.toList()
    }
}
