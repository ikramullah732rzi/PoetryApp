package com.iinnovation.hindishayari.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdsViewModel : ViewModel() {
    private var _ui_Event = MutableLiveData(0)
    val ui_Event: LiveData<Int> get() = _ui_Event

    private var c = 0

    fun onClickFunction() {
        c++
        Log.d("data","C is = $c")
        _ui_Event.postValue(c)
    }
}