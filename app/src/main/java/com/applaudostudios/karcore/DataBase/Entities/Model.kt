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
    @ColumnInfo(name = "is_favourite")
    val isFavourite: Boolean,
    val description: String,
    @ColumnInfo(name = "model_url")
    val modelUrl: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val photo: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Model

        if (id != other.id) return false
        if (modelName != other.modelName) return false
        if (isFavourite != other.isFavourite) return false
        if (modelUrl != other.modelUrl) return false
        if (!photo.contentEquals(other.photo)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + modelName.hashCode()
        result = 31 * result + isFavourite.hashCode()
        result = 31 * result + modelUrl.hashCode()
        result = 31 * result + photo.contentHashCode()
        return result
    }
}
