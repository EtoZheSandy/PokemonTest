package su.afk.pokemontest.screen.detailScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import su.afk.pokemontest.data.models.Pokemon
import su.afk.pokemontest.screen.detailScreen.PokemonViewModel
import su.afk.pokemontest.util.Resource

@Composable
fun PokemonDetailStateWrapper(
    pokemonInfo: Resource<Pokemon>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier,
    viewModel: PokemonViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope() // Создаем CoroutineScope

    when(pokemonInfo) {
        is Resource.Success -> {
            PokemonDetailSection(
                pokemonInfo = pokemonInfo.data!!,
                modifier = modifier
                    .offset(y = (-20).dp)
            )
        }
        is Resource.Error -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                RetrySection(
                    error = pokemonInfo.message!!,
                    onRetry = {
                        coroutineScope.launch { // Запускаем сопрограмму из CoroutineScope
                            viewModel.getPokemonInfo(pokemonInfo.data?.name)
                        }
                    }
                )
            }
        }
        is Resource.Loading -> {
            CircularProgressIndicator(
//                color = MaterialTheme.colors.primary,
                color = Color.Black,
                modifier = loadingModifier
            )
        }
    }
}