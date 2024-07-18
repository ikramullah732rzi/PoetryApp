package com.iinnovation.hindishayari.presentation

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.iinnovation.hindishayari.DestinationScreen
import com.iinnovation.hindishayari.MainActivity
import com.iinnovation.hindishayari.R
import com.iinnovation.hindishayari.admobintegration.bannerAd
import com.iinnovation.hindishayari.admobintegration.intertisialAd
import com.iinnovation.hindishayari.data.AllCatgory
import com.iinnovation.hindishayari.domain.AdsViewModel
import com.iinnovation.hindishayari.domain.MainViewModel
import com.iinnovation.hindishayari.domain.MyFactory
import com.iinnovation.hindishayari.listofDrawer
import com.iinnovation.hindishayari.ui.theme.backgroundcolor
import com.iinnovation.hindishayari.ui.theme.itemcolor
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.interstitial.InterstitialAd
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavHostController,
    mainActivity: MainActivity,
    mode: String,
    adsViewModel: AdsViewModel
) {
    val context = LocalContext.current
    val main: MainViewModel = viewModel<MainViewModel>(factory = MyFactory(context))

    val categorylist = main.poetryCategoryList.collectAsState()
    val categoryList = categorylist.value.shuffled()
    val scrollState = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val selectedIndex = rememberSaveable {
        mutableIntStateOf(10)
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val expendble = remember {
        mutableStateOf(false)
    }
    val contextt = LocalContext.current
    val shareAppUrl = "https://play.google.com/store/apps/details?id=com.iinnovation.hindishayari"
    val shareBody =
        "Enjoy beautiful poems, share your creations, and join a community of poetry lovers. Download $shareAppUrl now and start your poetic journey!"

    Scaffold{
        BackHandler(enabled = true) {
            // Show confirmation dialog before exiting
            val builder = AlertDialog.Builder(mainActivity)
            builder.setTitle("Exit Confirmation")
            builder.setMessage("Are you sure you want to exit?")
            builder.setPositiveButton("Yes") { dialog, which ->
               mainActivity.finish() // Close the activity
            }
            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            builder.create().show()
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollState.nestedScrollConnection)
        ) {

            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet(
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.8f)

                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .background(itemcolor)
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.Center

                        ) {
                            Spacer(modifier = Modifier.height(20.dp))
                            Image(
                                painter = painterResource(id = R.drawable.logoforground),
                                contentDescription = "quiz image",
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.TopCenter,
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .border(2.dp, Color.Gray, CircleShape)
                                    .padding(8.dp)
                                    .clickable {
                                    }
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                            Text(text = "Hindi Shayari", color = Color.White,modifier = Modifier.padding(bottom = 20.dp).align(Alignment.BottomCenter))
                           // Spacer(modifier = Modifier.height(30.dp))
                        }

                        listofDrawer.forEachIndexed { index, item ->
                            NavigationDrawerItem(
                                label = {
                                    Text(
                                        text = item.title, style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                },
                                selected = index == selectedIndex.intValue,
                                onClick = {
                                    selectedIndex.intValue = index
                                    coroutineScope.launch {
                                        drawerState.close()
                                        if (index == 0) {

                                            val intent = Intent(Intent.ACTION_SEND)
                                            intent.setType("text/plain")
                                            intent.putExtra(Intent.EXTRA_TEXT, shareBody)

                                            contextt.startActivity(
                                                Intent.createChooser(
                                                    intent,
                                                    shareBody
                                                )
                                            )
                                        } else if (index == 1) {
                                            val url = shareAppUrl
                                            val i = Intent(Intent.ACTION_VIEW)
                                            i.setData(Uri.parse(url))
                                            contextt.startActivity(i)
                                        } else if (index == 2) {
                                            val url =
                                                "https://sites.google.com/view/iinnovationaboutpage/home"
                                            val i = Intent(Intent.ACTION_VIEW)
                                            i.setData(Uri.parse(url))
                                            contextt.startActivity(i)
                                        } else if (index == 3) {
                                            val url =
                                                "https://sites.google.com/view/hindipoetryapp/home"
                                            val i = Intent(Intent.ACTION_VIEW)
                                            i.setData(Uri.parse(url))
                                            contextt.startActivity(i)
                                        }
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (index == selectedIndex.intValue) item.isSelected else item.notSelected,
                                        contentDescription = item.title
                                    )
                                },
                                modifier = Modifier

                            )
                        }

                    }
                },
                gesturesEnabled = true,
                modifier = Modifier,
                drawerState = drawerState
            ) {
                Scaffold(
                    bottomBar = {
                        if (mode.equals("open")) {
                            bannerAd()
                        }
                    },
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(itemcolor),
                            title = {
                                Text(
                                    text = "Hindi Poetry",
                                    color = Color.White
                                )
                            },
                            scrollBehavior = scrollState,
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            drawerState.open()
                                        }
                                    }
                                ) {
                                    Image(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Setting",
                                        colorFilter = ColorFilter.tint(Color.White)
                                    )
                                }
                            },
                            actions = {

                                    IconButton(onClick = {
                                        navController.navigate(DestinationScreen.FavuriteScreen.path) {
                                            popUpTo(DestinationScreen.FavuriteScreen.path)
                                            launchSingleTop = true
                                        }
                                    }) {
                                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorite icon", tint = Color.White, modifier = Modifier.padding(end = 10.dp))

                                    }
                                })

                    }
                ) {
                            LazyColumn(modifier = Modifier.padding(it)) {
                                itemsIndexed(categoryList) { index, item ->
                                    Sample(
                                        item,
                                        index = index + 1,
                                        navController,
                                        context,
                                        mainActivity,
                                        mode,
                                        adsViewModel
                                    )
                                }
                            }

                    }
                }
            }
        }


}



@Composable
fun Sample(
    allCatgory: AllCatgory,
    index: Int,
    navController: NavHostController,
    context: Context,
    mainActivity: MainActivity,
    mode: String,
    adsViewModel: AdsViewModel
) {
    val adRequest = AdRequest.Builder().build()
    var mInterstitialAd: InterstitialAd? = null
    val TAG = "MainActivity"
    val state = remember {
        mutableStateOf(0)
    }
    adsViewModel.ui_Event.observe(mainActivity) {
        state.value = it
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundcolor)
            .padding(start = 12.dp, end = 12.dp, top = 5.dp, bottom = 5.dp)
            .clip(RoundedCornerShape(20))
            .background(
                itemcolor
            )
            .clickable {

                if (mode.equals("open")) {
                    adsViewModel.onClickFunction()
                    if (state.value.equals(0)) {
                        intertisialAd(mainActivity, adRequest) { it ->
                            mInterstitialAd = it
                            mInterstitialAd!!.show(mainActivity)
                        }
                        mInterstitialAd?.fullScreenContentCallback =
                            object : FullScreenContentCallback() {
                                override fun onAdClicked() {
                                    // Called when a click is recorded for an ad.
                                    Log.d(TAG, "Ad was clicked.")
                                }

                                override fun onAdDismissedFullScreenContent() {
                                    // Called when ad is dismissed.
                                    Log.d(TAG, "Ad dismissed fullscreen content.")
                                    mInterstitialAd = null
                                }

                                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                                    // Called when ad fails to show.
                                    Log.e(TAG, "Ad failed to show fullscreen content.")
                                    mInterstitialAd = null
                                }

                                override fun onAdImpression() {
                                    // Called when an impression is recorded for an ad.
                                    Log.d(TAG, "Ad recorded an impression.")
                                }

                                override fun onAdShowedFullScreenContent() {
                                    // Called when ad is shown.
                                    Log.d(TAG, "Ad showed fullscreen content.")
                                }
                            }
                    }

                }
                navController.navigate("DetailScreen/" + allCatgory.id + "/" + allCatgory.name) {
                    popUpTo(DestinationScreen.DetailScreen.path)
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

