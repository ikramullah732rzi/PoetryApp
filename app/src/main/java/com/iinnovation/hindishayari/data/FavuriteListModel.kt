package com.iinnovation.hindishayari.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favurite_table")
data class FavuriteListModel(
    @PrimaryKey(autoGenerate = true) var uid: Int?=null,
    @ColumnInfo(name = "poetry") var poetry: String?,
)