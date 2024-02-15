package com.example.poetryapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
@Entity(tableName = "AllShayariCategory")
 class AllCatgory(

 @PrimaryKey(autoGenerate = true)val uid : Int?=null,
  @ColumnInfo(name = "name")val name : String?,
  @ColumnInfo(name="id")val id : Int?,
)*/

@Entity(tableName = "AllShayariCategory")
class AllCatgory(

 @PrimaryKey(autoGenerate = true) var uid : Int?=null,
 @ColumnInfo(name = "name") var name : String?,
 @ColumnInfo("id") var id: Int?
)