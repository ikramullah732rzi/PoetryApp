package com.iinnovation.hindishayari.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [AllShayari::class, AllCatgory::class,FavuriteListModel::class], version = 4, exportSchema = false)
abstract class DatabasePoetry:RoomDatabase() {
    abstract fun getDao() :Dao
}