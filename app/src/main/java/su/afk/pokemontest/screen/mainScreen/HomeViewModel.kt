package su.afk.pokemontest.screen.mainScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import su.afk.pokemontest.data.PokemonRepository
import su.afk.pokemontest.screen.mainScreen.model.PokemonEntry
import su.afk.pokemontest.util.Resource
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val repository: PokemonRepository
): ViewModel() {

    var pokemonList = mutableStateOf<List<PokemonEntry>>(listOf())

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(true)// для статуса загрузки

    private var cachePokemonList = listOf<PokemonEntry>()
    private var isSearchStarting = true


    init {
        loadListPokemon()
    }

    fun loadListPokemon() {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {

            val result = repository.getPokemonList(10000, 0)
            when (result) {
                is Resource.Success -> {
//                    pokemonList.value = result.data?.results ?: listOf()
                    val tempPokemon = result.data!!.results!!.mapIndexed { index, pokeon ->
                        val number = if(pokeon.url.endsWith("/")) {
                            pokeon.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            pokeon.url.takeLastWhile { it.isDigit() }
                        }
                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokemonEntry(pokeon.name, url, number.toInt())
                    }

                    pokemonList.value = tempPokemon
                    loadError.value = ""
                    isLoading.value = false
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                is Resource.Loading -> {
                }
            }
        }
    }

    fun searchOffline(query: String) {
        val listToSearch = if (isSearchStarting) {
            pokemonList.value
        } else {
            cachePokemonList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) { //если перестали искать и очиситили поле ввода
                pokemonList.value =
                    cachePokemonList // если строка поиска пуста или удалена мы возвращаем ее из кэша
                isSearchStarting = true
                return@launch
            }

            val results = listToSearch.filter {
                // поиск по названию с удалением пробелов и регистров
                it.name.contains(query.trim(), ignoreCase = true)
            }
            //сработает при первом запуске поиска
            if (isSearchStarting) {
                cachePokemonList = pokemonList.value
                isSearchStarting = false
            }
            pokemonList.value = results
        }
    }
}