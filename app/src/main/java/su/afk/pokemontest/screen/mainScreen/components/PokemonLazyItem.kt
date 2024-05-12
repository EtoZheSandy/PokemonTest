package su.afk.pokemontest.screen.mainScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import su.afk.pokemontest.data.models.Result
import su.afk.pokemontest.screen.mainScreen.HomeViewModel
import su.afk.pokemontest.screen.mainScreen.model.PokemonEntry
import su.afk.pokemontest.ui.theme.BackgroundLazy

@Composable
fun PokemonLazyItem(
    pokemon: PokemonEntry, // каждый покемон
    navController: NavController, // для навигации по id
    modifier: Modifier = Modifier, // дефолтный Modifier
) {
    var isLoading by remember { mutableStateOf(true) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(6.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(BackgroundLazy)
            .aspectRatio(1f)
            .clickable {
                navController.navigate(
                    "pokemon_detail_screen/${pokemon.name}"
                )
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = pokemon.name,
                onSuccess = {
                    isLoading = false
                },
                contentScale = ContentScale.Crop,
                modifier = Modifier
//                    .fillMaxWidth()
                    .size(160.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 14.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        Text(
            text = pokemon.name,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 23.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp),
            maxLines = 1 // Максимальное количество строк
        )
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}