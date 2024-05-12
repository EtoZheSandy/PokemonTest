package su.afk.pokemontest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import su.afk.pokemontest.screen.detailScreen.ScreenDetail
import su.afk.pokemontest.screen.mainScreen.ScreenHome

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "pokemon_list_screen"
    ) {

        composable("pokemon_list_screen") {
            ScreenHome(navController = navController)
        }
        composable(
            "pokemon_detail_screen/{pokemonName}",
            arguments = listOf(
                navArgument("pokemonName") {
                    type = NavType.StringType
                }
            )
        ) {
            val pokemonName = remember {
                it.arguments?.getString("pokemonName")
            }
            ScreenDetail(
                pokemonName = pokemonName ?: "",
                navController = navController
            )
        }
    }
}
