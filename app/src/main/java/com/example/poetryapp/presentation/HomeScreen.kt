package com.example.poetryapp.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.poetryapp.DestinationScreen
import com.example.poetryapp.data.AllCatgory
import com.example.poetryapp.data.DbBuilder
import com.example.poetryapp.ui.theme.backgroundcolor
import com.example.poetryapp.ui.theme.itemcolor


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(itemcolor), title = {
                Text(text = "Poetry App", color = Color.White)
            }, actions = {
                IconButton(onClick = {
                    navController.navigate(DestinationScreen.FavuriteScreen.path) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorite icon", tint = Color.White, modifier = Modifier.padding(end = 10.dp))

                }
            })
        }
    ) {
        val context = LocalContext.current
        val list = DbBuilder.getalldatafromAssets(context).getDao().getCatagory()
        // list.shuffled()
        LazyColumn(modifier = Modifier.padding(it)) {
            itemsIndexed(list) { index, item ->
                Sample(
                    item,
                    index = index + 1,
                    navController,
                    context
                )
            }
        }
    }
}


@Composable
fun Sample(allCatgory: AllCatgory, index: Int, navController: NavHostController, context: Context) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundcolor)
            .padding(start = 12.dp, end = 12.dp, top = 10.dp)
            .clip(RoundedCornerShape(20))
            .background(
                itemcolor
            )
            .clickable {
                navController.navigate("DetailScreen/" + allCatgory.id + "/" + allCatgory.name){
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
    ) {

        Text(
            text = "${allCatgory.name}",
            color = Color.White,
            modifier = Modifier.padding(15.dp)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Arrow Farward",
            tint = Color.White,
            modifier = Modifier.padding(end = 5.dp)
        )
    }

}




