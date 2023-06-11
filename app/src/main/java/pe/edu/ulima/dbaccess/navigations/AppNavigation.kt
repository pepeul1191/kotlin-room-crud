package pe.edu.ulima.dbaccess.navigations

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pe.edu.ulima.dbaccess.ui.app.screens.HomeScreen
import pe.edu.ulima.dbaccess.ui.app.screens.PokemonDetailScreen
import pe.edu.ulima.dbaccess.ui.app.viewmodels.HomeViewModel
import pe.edu.ulima.dbaccess.ui.app.viewmodels.PokemonDetailViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val pokemonIdParam = navBackStackEntry?.arguments?.getInt("pokemon_id")
    val context = LocalContext.current
    val activity = context as Activity

    HomeScreen(
        viewModel = HomeViewModel(),
        navController
    )
    /*
    NavHost(
        navController = navController,
        startDestination = "/"
    ){
        // vista para mostrar el listado de pokemones
        composable(
            route = "/",
            arguments = listOf(
            )
        ){

            HomeScreen(
                viewModel = HomeViewModel(),
                navController
            )
        }
        //editar pokemon
        composable(
            route = "/pokemon/edit?pokemon_id={pokemon_id}",
            arguments = listOf(
                navArgument("pokemon_id"){
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ){
            Log.d("APP_NAVIGATION", pokemonIdParam.toString())
            val viewModel: PokemonDetailViewModel = PokemonDetailViewModel()
            viewModel.getPokemon(pokemonIdParam!!, context)
            PokemonDetailScreen(
                viewModel = viewModel,
                navController
            )
        }
        // crear pokemon
        composable(
            route = "/pokemon/new",
            arguments = listOf(
            )
        ){
            val viewModel: PokemonDetailViewModel = PokemonDetailViewModel()
            PokemonDetailScreen(
                viewModel = viewModel,
                navController
            )
        }
    }
    */
}