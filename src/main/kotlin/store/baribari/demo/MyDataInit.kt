package store.baribari.demo

import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import store.baribari.demo.common.enums.Role
import store.baribari.demo.model.Store
import store.baribari.demo.model.User
import store.baribari.demo.model.cart.Cart
import store.baribari.demo.model.embed.Day
import store.baribari.demo.model.menu.Banchan
import store.baribari.demo.model.menu.BanchanDosirak
import store.baribari.demo.model.menu.Dosirak
import store.baribari.demo.repository.StoreRepository
import store.baribari.demo.repository.UserRepository
import store.baribari.demo.repository.cart.CartRepository
import store.baribari.demo.repository.menu.BanchanDosirakRepository
import store.baribari.demo.repository.menu.BanchanRepository
import store.baribari.demo.repository.menu.DosirakRepository
import java.time.DayOfWeek
import java.time.LocalTime
import javax.annotation.PostConstruct
import kotlin.math.nextUp

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

        val store = Store(
            owner = userList[1],
            name = "test store",
            phoneNumber = "010-1234-5678",
            address = "서울시 강남구 테헤란로 427",
            businessName = "테스트 가게",
            businessNumber = "123-45-67890",
            description = "테스트 가게입니다.",
            //dayList = dayList,
        )
        storeRepository.saveAndFlush(store)
        val banchanList = banchanListMaker(store)
        val dosirakList = dosirakListMaker(store)
        banchanDosirakMatchMaker(banchanList, dosirakList)

    }

    private fun banchanDosirakMatchMaker(
        banchanList: List<Banchan>,
        dosirakList: List<Dosirak>
    ) {
        val banchanDosirakList = mutableListOf<BanchanDosirak>()
        for (i in dosirakList.indices) {
            for (j in 0..2) {
                banchanDosirakList.add(
                    BanchanDosirak(
                        banchan = banchanList[i * 3 + j],
                        dosirak = dosirakList[i]
                    )
                )
            }
        }
        banchanDosirakRepository.saveAll(banchanDosirakList)
    }

    private fun banchanListMaker(
        store: Store
    ): List<Banchan> {
        val ret = mutableListOf<Banchan>()

        for (i in 1..30) {
            ret.add(
                Banchan(
                    name = "테스트 반찬 $i",
                    description = "테스트 반찬입니다. $i",
                    gram = 100,
                    owner = store,
                )
            )
        }
        banchanRepository.saveAll(ret)
        return ret
    }

    private fun dayList(): MutableList<Day> {
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

        return mutableListOf(monday, tuesday, wednesday, thursday)
    }

    private fun userCreate(): List<User> {

        val customer = User(
            email = "customer@test.com", password = "customer", role = Role.ROLE_CUSTOMER, userCart = Cart()
        )
        val encodedPassword1 = passwordEncoder.encode(customer.password)
        customer.encodePassword(encodedPassword1)

        val storeOwner = User(
            email = "store@test.com", password = "store", role = Role.ROLE_STORE, userCart = Cart()
        )
        val encodedPassword2 = passwordEncoder.encode(storeOwner.password)
        storeOwner.encodePassword(encodedPassword2)

        userRepository.saveAll(listOf(customer, storeOwner))
        userRepository.flush()

        return listOf(customer, storeOwner)
    }

    private fun dosirakListMaker(store: Store): List<Dosirak> {
        val tempList = mutableListOf<Dosirak>()

        for (i in 1..10) {
            val temp = Dosirak(
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
