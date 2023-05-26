package store.baribari.demo.common.enums

enum class Role(
    val code: String,
    val displayName: String,
) {
    ROLE_ADMIN("ROLE_ADMIN", "관리자 권한"),
    ROLE_CUSTOMER("ROLE_CUSTOMER", "일반 소비자"),
    ROLE_STORE("ROLE_STORE", "가게 주인"),
    ;

    companion object {
        fun of(code: String): Role {
            return Role.values()
                .find { role -> role.code == code } ?: ROLE_CUSTOMER
        }
    }
}
