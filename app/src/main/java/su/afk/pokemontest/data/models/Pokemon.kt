package su.afk.pokemontest.data.models

data class Pokemon(
    val id: Int,
    val name: String,
    val stats: List<Stat>,
    val types: List<Type>
)
