package com.iinnovation.hindishayari.admobintegration

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.iinnovation.hindishayari.MainActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback


@Composable
fun bannerAd() {
    AndroidView(modifier = Modifier.fillMaxWidth(), factory = {
        AdView(it).apply {
            setAdSize(
                AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                    it,
                    AdSize.FULL_WIDTH
                )
            )
            adUnitId = ADSID.BANNERID
            loadAd(AdRequest.Builder().build())
        }
    })
}
const val ADMOB="Admob"
fun intertisialAd(activity: MainActivity,adRequest: AdRequest, todoReturn : (InterstitialAd)->Unit){
    val TAG = "MainActivity"
   // Toast.makeText(activity, "open from interstitial add", Toast.LENGTH_SHORT).show()
    var mInterstitialAd: InterstitialAd? = null
    InterstitialAd.load(
        activity,
        ADSID.INTERTISIALADID,
        adRequest,object : InterstitialAdLoadCallback(){
            override fun onAdFailedToLoad(p0: LoadAdError) {
                Log.i(ADMOB,"InterstitialAd Failed to Load")
            }

            override fun onAdLoaded(p0: InterstitialAd) {
                super.onAdLoaded(p0)
                mInterstitialAd = p0
               todoReturn(mInterstitialAd!!)
            }
        })
}

fun rewardedAd(activity: MainActivity){
    RewardedAd.load(
        activity,
        ADSID.REWAREDEADDID,
        AdRequest.Builder().build(),object : RewardedAdLoadCallback(){
            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                Log.i(ADMOB,"Rewarded Add Failed to Load")

            }
            override fun onAdLoaded(p0: RewardedAd) {
                super.onAdLoaded(p0)
                p0.show(activity){}
            }
        })
}

fun rewardandinterstiatialadd(activity: MainActivity){
    RewardedInterstitialAd.load(activity,ADSID.REWARDEDANDINTERSTITIAL,
        AdRequest.Builder().build(),object : RewardedInterstitialAdLoadCallback(){
            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                Log.i(ADMOB, "onAdFailedToLoad: rewared and interstitial")
            }

            override fun onAdLoaded(p0: RewardedInterstitialAd) {
                super.onAdLoaded(p0)
                p0.show(activity){}
            }
        })
}
object ADSID {
    const val BANNERID = "ca-app-pub-3940256099942544/9214589741"
    const val INTERTISIALADID = "ca-app-pub-3940256099942544/1033173712"
    const val REWAREDEADDID = "ca-app-pub-3940256099942544/5224354917"
    const val REWARDEDANDINTERSTITIAL = "ca-app-pub-3940256099942544/5354046379"
}