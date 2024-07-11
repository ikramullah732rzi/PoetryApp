package com.iinnovation.hindishayari.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/*@Entity(tableName = "AllShayari")
 class AllShayari(


     @PrimaryKey(autoGenerate = true) val Catid:Int?=null,
     @ColumnInfo(name="uid") val uid : Int?=null,
    @ColumnInfo(name = "Shayari") val Shayari:String?=null,

)*/


@Entity(tableName = "AllShayari")
class AllShayari(
    @PrimaryKey(autoGenerate = true) var uid: Int? = null,
    @ColumnInfo(name = "Cat_id") var Cat_id: Int?=null ,
    @ColumnInfo(name = "Shayari") var Shayari: String?=null,

    )
