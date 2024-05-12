package su.afk.pokemontest.data

import dagger.hilt.android.scopes.ActivityScoped
import su.afk.pokemontest.data.models.Pokemon
import su.afk.pokemontest.data.remote.PokemonApi
import su.afk.pokemontest.data.models.PokemonList
import su.afk.pokemontest.util.Resource
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokemonApi
){

    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch(e: Exception) {
            return Resource.Error("Произошла неизвестная ошибка..")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfoName(pokemonName: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch(e: Exception) {
            return Resource.Error("Произошла неизвестная ошибка..")
        }
        return Resource.Success(response)
    }
}