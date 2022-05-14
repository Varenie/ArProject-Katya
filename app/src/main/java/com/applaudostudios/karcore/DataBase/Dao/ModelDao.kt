package com.applaudostudios.karcore.DataBase.Dao

import androidx.room.*
import com.applaudostudios.karcore.DataBase.Entities.Model

@Dao
interface ModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertModel(model: Model)

    @Update
    fun updateModel(model: Model)

    @Delete
    fun deleteModel(model: Model)

    @Query("SELECT * FROM Model")
    fun getModels(): List<Model>

    @Query("SELECT * FROM Model WHERE is_favourite == 'TRUE'")
    fun getFavouriteModels(): List<Model>

    @Query("DELETE FROM Model WHERE id < 5")
    fun deleteAll()
}