package store.baribari.demo.dto

import store.baribari.demo.model.menu.Banchan

data class BanchanNameAndGramDto(
    val banchanName: String,
    val gram: Int,
){
    companion object{
        fun fromBanchanToDto(banchan: Banchan): BanchanNameAndGramDto{
            return BanchanNameAndGramDto(
                banchanName = banchan.name,
                gram = banchan.gram,
            )
        }
    }
}
