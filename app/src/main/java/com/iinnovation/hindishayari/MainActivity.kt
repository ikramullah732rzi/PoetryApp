package com.iinnovation.hindishayari

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.iinnovation.hindishayari.api.ApiEndpoints
import com.iinnovation.hindishayari.api.ClientApi
import com.iinnovation.hindishayari.domain.AdsViewModel
import com.iinnovation.hindishayari.presentation.DetailScreen
import com.iinnovation.hindishayari.presentation.FavuriteScreen
import com.iinnovation.hindishayari.presentation.HomeScreen
import com.iinnovation.hindishayari.presentation.SplishedScreen
import com.iinnovation.hindishayari.ui.theme.PoetryAppTheme
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val AIR_TABLE_TOKEN =
    "pat1vNooEeDe8Uy2W.9e64c3a62bcae66d404d74fca761cc84c6e7f3d2dd6b28d57ce9a2735e9b8ec5"
const val BASE_ID = "apprtKbmTnTCmmVVR"
const val TABLE_NAME = "PoetryApp1"

class MainActivity : ComponentActivity() {

    private var mode: String = "close" // Initialize with empty string
    private lateinit var adsViewModel: AdsViewModel

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adsViewModel = ViewModelProvider(this)[AdsViewModel::class.java]
        MobileAds.initialize(this) {}
        setContent {
            val mutableMode = remember { mutableStateOf(mode) } // Initially empty
            PoetryAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    MainApp(
                        this@MainActivity, mutableMode.value, adsViewModel
                    ) // Pass fetched value
                }


            }
        }
       /* // Check for internet connectivity before API call
        if (isConnectedToNetwork()) {
            val retrofit = ClientApi.getApiClient().create(ApiEndpoints::class.java)

            lifecycleScope.launch {
                val response = retrofit.getPermission(TABLE_NAME, "Bearer $AIR_TABLE_TOKEN").body()
                if (response != null) {
                    mode = response.records[0].fields.AdsMode
                    Log.d("msg", "$mode is fetched successfully")

                    // Update UI directly from coroutine using withContext(Dispatchers.Main) { ... }**
                    withContext(Dispatchers.Main) {
                        // Update mutableMode here
                        setContent {
                            val mutableMode = remember { mutableStateOf(mode) } // Initially empty
                            PoetryAppTheme {
                                Surface(
                                    modifier = Modifier.fillMaxSize(),
                                    color = MaterialTheme.colorScheme.background
                                ) {
                                    if (response.records.isNotEmpty()) {
                                        MainApp(
                                            this@MainActivity, mutableMode.value, adsViewModel
                                        ) // Pass fetched value
                                    } else {
                                        // Show a loading indicator or placeholder while waiting
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text("No internet connection")
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Log.e("msg", "Error fetching API response")
                    // Handle API error (optional)
                }
            }
        } else {
            // Show a message indicating no internet connection
            // Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            setContent {
                val mutableMode = remember { mutableStateOf(mode) } // Initially empty
                PoetryAppTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        MainApp(
                            this@MainActivity, mutableMode.value, adsViewModel
                        ) // Pass fetched value
                    }


                }
            }
        }*/
    }

    // Function to check internet connectivity (replace with your preferred method)
    private fun isConnectedToNetwork(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

}



@Composable
fun MainApp(mainActivity: MainActivity, mode: String, adsViewModel: AdsViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = DestinationScreen.SplishedScreen.path
    ) {

        composable(DestinationScreen.HomeScreen.path) {
            HomeScreen(navController = navController, mainActivity, mode, adsViewModel)
        }
        composable(DestinationScreen.FavuriteScreen.path) {
            FavuriteScreen(navController)
        }
        composable(DestinationScreen.SplishedScreen.path) {
            SplishedScreen(navController)
        }
        composable(DestinationScreen.DetailScreen.path, arguments = listOf(navArgument("id") {
            type = NavType.IntType
        }, navArgument("title") {
            type = NavType.StringType
        })) {
            val id = it.arguments?.getInt("id")
            val title = it.arguments?.getString("title")
            DetailScreen(id, title, navController)
        }
    }
}

sealed class DestinationScreen(var path: String) {
    object HomeScreen : DestinationScreen("HomeScreen")
    object FavuriteScreen : DestinationScreen("FavuriteScreen")
    object SplishedScreen : DestinationScreen("SplishedScreen")
    object DetailScreen : DestinationScreen("DetailScreen/{id}/{title}") {
        fun getid(id: Int, title: String): String {
            return "DetailScreen/$id/$title"
        }
    }
}