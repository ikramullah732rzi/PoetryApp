package com.iinnovation.hindishayari.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.iinnovation.hindishayari.DestinationScreen
import com.iinnovation.hindishayari.R
import com.iinnovation.hindishayari.ui.theme.backgroundcolor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay


@Composable
fun SplishedScreen(navController: NavHostController) {


    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(backgroundcolor)
    }

    var shouldShowScreen by remember { mutableStateOf(true) }
    LaunchedEffect(key1 = shouldShowScreen) {
        if (shouldShowScreen) {
            // Delay for 2000 milliseconds
                delay(2000)
            // Navigate to HomeScreen
            navController.navigate(DestinationScreen.HomeScreen.path) {
                // Pop back stack to ensure SplashScreen won't be reachable again
                navController.popBackStack()

            }
            // Set shouldShowScreen to false to ensure this effect runs only once
            shouldShowScreen = false
        }
    }

    Box(contentAlignment = Alignment.Center) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundcolor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.logoforground),
                contentDescription = "logo",
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.White)
                    .clip(CircleShape)
            )

            Text(
                text = "Hindi Shayari",
                fontSize = 30.sp,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold
            )


        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp)
        ) {
            Text(
                text = "All rights are reserved",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()

            )
        }


    }

}




