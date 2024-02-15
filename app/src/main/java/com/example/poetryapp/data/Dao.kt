package com.example.poetryapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {


    //----------------------------------------
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(favuriteListModel: FavuriteListModel)

    @Delete
    fun delete(favuriteListModel: FavuriteListModel)

    @Query("SELECT * FROM Favurite_table")
    fun getAllFavuriteList() : LiveData<List<FavuriteListModel>>



/*
  @Delete
    fun delete(favuriteListModel: FavuriteListModel)

    @Query("SELECT * FROM Favurite_table")
    fun getAllFavuriteList() : LiveData<List<FavuriteListModel>>



*/






// ---------------------------------------------
    @Query("select * from AllShayariCategory")
    fun getCatagory() :List<AllCatgory>

    @Query("select * from AllShayari where Cat_id =:id")
    fun getFilterShayari(id :Int) :List<AllShayari>

}