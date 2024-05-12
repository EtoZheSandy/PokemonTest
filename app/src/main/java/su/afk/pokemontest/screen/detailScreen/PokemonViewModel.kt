package su.afk.pokemontest.screen.detailScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import su.afk.pokemontest.data.PokemonRepository
import su.afk.pokemontest.data.models.Pokemon
import su.afk.pokemontest.util.Resource
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val repository: PokemonRepository,
) : ViewModel() {

    private val _pokemonInfo = MutableStateFlow<Resource<Pokemon>>(Resource.Loading())
    val pokemonInfo: StateFlow<Resource<Pokemon>> = _pokemonInfo

    suspend fun getPokemonInfo(name: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val pokemon = if (name == "1" || name == null) {
                repository.getPokemonInfoName("bulbasaur")
            } else {
                repository.getPokemonInfoName(name)
            }
            _pokemonInfo.value = pokemon
        }
    }

}