package su.afk.pokemontest.screen.detailScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import su.afk.pokemontest.screen.detailScreen.components.PokemonDetailStateWrapper
import su.afk.pokemontest.screen.detailScreen.components.PokemonDetailTopSection
import su.afk.pokemontest.util.Resource

@Composable
fun ScreenDetail(
    pokemonName: String,
    navController: NavController,
    viewModel: PokemonViewModel = hiltViewModel(),
    pokemonImageSize: Dp = 400.dp,
) {
    val pokemonInfo by viewModel.pokemonInfo.collectAsState()

    // делаем запрос по переданному имени
    LaunchedEffect(pokemonName) {
        viewModel.getPokemonInfo(pokemonName)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
        ) {
            PokemonDetailTopSection(
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .align(Alignment.TopCenter)
            )
            PokemonDetailStateWrapper(
                pokemonInfo = pokemonInfo,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top =  50.dp + pokemonImageSize / 2f,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                    .shadow(10.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                viewModel = viewModel
            )

            if (pokemonInfo is Resource.Success) {
                pokemonInfo.data?.let {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${it.id}.png")
                            .crossfade(true)
                            .build(),
                        contentDescription = it.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(pokemonImageSize)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }

        }
    }
}

