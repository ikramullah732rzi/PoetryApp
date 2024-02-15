package com.example.poetryapp.presentation

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.poetryapp.DestinationScreen
import com.example.poetryapp.data.DbBuilder
import com.example.poetryapp.data.FavuriteListModel
import com.example.poetryapp.ui.theme.backgroundcolor
import com.example.poetryapp.ui.theme.itemcolor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavuriteScreen(navController: NavHostController) {

    val context = LocalContext.current
    val clist by DbBuilder.getalldatafromAssets(context).getDao().getAllFavuriteList().observeAsState()




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
                                    navController.popBackStack()

                                }
                            }
                    )
                },
                title = {
                    Text(
                        text = "Favurite Poetry",
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
                .padding(it)
        ) {
            LazyColumn() {

                clist?.let {
                    items(it.size) {

                        FavuriteSample(clist,it)

                    }
                }
            }
        }

    }


}

@Composable
fun FavuriteSample(clist: List<FavuriteListModel>?, i: Int) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundcolor)
    ) {
        Text(
            text = "${clist!!.get(i).poetry}",
            color = Color.White,
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
                    itemcolor
                )
        ) {
            Icon(
                imageVector = Icons.Filled.ContentCopy,
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
                        val clipboard =
                            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val txtCopy = clist!!.get(i).poetry
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
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorite icon",
                tint = Color.White,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .background(
                        color = itemcolor,
                        CircleShape
                    )
                    .padding(10.dp)
                    .clickable {
                        DbBuilder
                            .getFavuriteListDatabase(context)
                            .getDao()
                            .delete(
                                FavuriteListModel(
                                    clist.get(i).uid,
                                    clist.get(i).poetry,
                                )
                            )
                        Toast
                            .makeText(context, "Data deleted to Favurite", Toast.LENGTH_SHORT)
                            .show()
                    }
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
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "${clist!!.get(i).poetry}")
                        sendIntent.setType("text/plain")
                        context.startActivity(Intent.createChooser(sendIntent, ""))
                        context.startActivity(sendIntent)
                    }
            )


        }

    }

}