package com.example.poetryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.poetryapp.presentation.DetailScreen
import com.example.poetryapp.presentation.FavuriteScreen
import com.example.poetryapp.presentation.HomeScreen
import com.example.poetryapp.presentation.SplishedScreen
import com.example.poetryapp.ui.theme.PoetryAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PoetryAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}
@Composable
fun MainApp() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = DestinationScreen.SplishedScreen.path
    ) {

        composable(DestinationScreen.HomeScreen.path) {
            HomeScreen(navController = navController)
        }
        composable(DestinationScreen.FavuriteScreen.path) {
            FavuriteScreen(navController)
        }
        composable(DestinationScreen.SplishedScreen.path) {
            SplishedScreen(navController)
        }
        composable(
            DestinationScreen.DetailScreen.path,
            arguments = listOf(
                navArgument("id") {
                type = NavType.IntType
            },
                navArgument("title"){
                    type= NavType.StringType
                }
            )
        ) {
            val id = it.arguments?.getInt("id")
            val title = it.arguments?.getString("title")
            DetailScreen(id,title,navController )
        }
    }
}
sealed class DestinationScreen(var path: String) {
    object HomeScreen : DestinationScreen("HomeScreen")
    object FavuriteScreen : DestinationScreen("FavuriteScreen")
    object SplishedScreen : DestinationScreen("SplishedScreen")
    object DetailScreen : DestinationScreen("DetailScreen/{id}/{title}") {
        fun getid(id: Int , title :String): String {
            return "DetailScreen/$id/$title"
        }
    }
}