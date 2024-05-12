package su.afk.pokemontest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import su.afk.pokemontest.navigation.AppNavigation
import su.afk.pokemontest.ui.theme.PokemonTestTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonTestTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}
