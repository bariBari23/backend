package store.baribari.demo.domain.menu.dto

import store.baribari.demo.domain.menu.entity.Banchan

data class BanchanNameAndGramDto(
    val banchanName: String,
    val gram: Int,
) {
    companion object {
        fun fromBanchanToDto(banchan: Banchan): BanchanNameAndGramDto =
            BanchanNameAndGramDto(
                banchanName = banchan.name,
                gram = banchan.gram,
            )
    }
}
