package su.afk.pokemontest.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import su.afk.pokemontest.data.models.Pokemon
import su.afk.pokemontest.data.models.PokemonList

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): Pokemon
}