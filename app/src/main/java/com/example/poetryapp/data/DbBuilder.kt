package com.example.poetryapp.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

object DbBuilder {


    private var INSTANCE: DatabasePoetry? = null

    @OptIn(InternalCoroutinesApi::class)
    fun getalldatafromAssets(context: Context): DatabasePoetry {
        if (INSTANCE == null) {

            synchronized(DatabasePoetry::class) {

              INSTANCE =  Room.databaseBuilder(context, DatabasePoetry::class.java, "mydbfile.db")
                    .allowMainThreadQueries().setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                    .fallbackToDestructiveMigration().createFromAsset("Shayari.db").build()
            }
        }

        return INSTANCE!!
    }
    @OptIn(InternalCoroutinesApi::class)
    fun getFavuriteListDatabase(context: Context): DatabasePoetry {
        if (INSTANCE == null) {

            synchronized(DatabasePoetry::class) {

              INSTANCE =  Room.databaseBuilder(context, DatabasePoetry::class.java, "favurite.db")
                    .allowMainThreadQueries().setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                    .fallbackToDestructiveMigration().build()
            }
        }

        return INSTANCE!!
    }



}