package com.iinnovation.hindishayari.domain

import android.content.Context
import androidx.lifecycle.ViewModel
import com.iinnovation.hindishayari.data.AllCatgory
import com.iinnovation.hindishayari.data.AllShayari
import com.iinnovation.hindishayari.data.DbBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(val context: Context):ViewModel() {

    // For Category List
    private  val _poetryCategoryList = MutableStateFlow<List<AllCatgory>>(emptyList())
    val poetryCategoryList = this._poetryCategoryList.asStateFlow()

    // For Detail List
    private val _detailList = MutableStateFlow<List<AllShayari>>(emptyList())
    val detailList = _detailList.asStateFlow()


    init {
        fetchCategories()
    }

    fun fetchCategories(){
        this._poetryCategoryList.update {
            DbBuilder.getalldatafromAssets(context).getDao().getCatagory()
        }
    }

    fun getAllShayari(id: Int){
        _detailList.update {
            DbBuilder.getalldatafromAssets(context).getDao().getFilterShayari(id)
        }
    }


}