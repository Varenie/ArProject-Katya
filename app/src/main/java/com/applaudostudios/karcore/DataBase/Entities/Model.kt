package com.applaudostudios.karcore.DataBase.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Model(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "model_name")
    val modelName: String,
    @ColumnInfo(name = "model_uri")
    val modelUri: String,
    @ColumnInfo(name = "photo_uri")
    val photoUri: String
)
