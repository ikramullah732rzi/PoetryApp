package com.example.poetryapp.presentation

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Copyright
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.poetryapp.DestinationScreen
import com.example.poetryapp.data.AllShayari
import com.example.poetryapp.data.DbBuilder
import com.example.poetryapp.data.FavuriteListModel
import com.example.poetryapp.ui.theme.backgroundcolor
import com.example.poetryapp.ui.theme.itemcolor
import com.example.poetryapp.ui.theme.rowcolor
import java.util.Random


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(id: Int?, title: String?, navController: NavHostController) {

    val context = LocalContext.current
    val dlist = DbBuilder.getalldatafromAssets(context).getDao().getFilterShayari(id!!)

    dlist.shuffled()
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Image(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back to Parent Screen",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .clickable {
                                navController.navigate(DestinationScreen.HomeScreen.path) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                            }
                    )
                },
                title = {
                    Text(
                        text = "${title}",
                        color = Color.White,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(itemcolor)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(itemcolor)
                .padding(it)
        ) {
            LazyColumn() {
                items(dlist) {
                    DetailSample(it)
                }
            }
        }
    }

}
@Composable
fun DetailSample(allShayari: AllShayari) {
    val context = LocalContext.current
    var favurite_button by remember {
        mutableStateOf(false)
    }

    var copy_button_state by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
    ) {
        Text(
            text = "${allShayari.Shayari}",
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
                .background(
                    rowcolor
                )
        ) {
            Icon(
                imageVector = if (copy_button_state) Icons.Filled.Copyright else Icons.Outlined.ContentCopy,
                contentDescription = "Copy icon",
                tint = Color.White,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .background(
                        color = itemcolor,
                        CircleShape
                    )
                    .padding(10.dp)
                    .clickable {
                        copy_button_state = !copy_button_state
                        val clipboard =
                            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val txtCopy = allShayari.Shayari
                        // clip data is initialized with the text variable declared above
                        val clipData = ClipData.newPlainText("text", txtCopy)
                        // Clipboard saves this clip object
                        Toast
                            .makeText(context, "Copied to ClipBoard", Toast.LENGTH_SHORT)
                            .show()
                        clipboard.setPrimaryClip(clipData)

                    }
            )
            Icon(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .background(
                        color = itemcolor,
                        CircleShape
                    )
                    .padding(10.dp)
                    .clickable {
                        favurite_button = !favurite_button
                        DbBuilder
                            .getFavuriteListDatabase(context)
                            .getDao()
                            .insert(FavuriteListModel(poetry = allShayari.Shayari))
                        Toast
                            .makeText(context, "Data add to Favurite", Toast.LENGTH_SHORT)
                            .show()
                    },
                imageVector = if (favurite_button) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite icon",
                tint = Color.White,
            )
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "Share icon",
                tint = Color.White,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .background(
                        color = itemcolor,
                        CircleShape
                    )
                    .padding(10.dp)
                    .clickable {


                        val sendIntent = Intent()
                        sendIntent.setAction(Intent.ACTION_SEND)
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "${allShayari.Shayari}")
                        sendIntent.setType("text/plain")
                        context.startActivity(sendIntent)
                    }
            )


        }

    }

}