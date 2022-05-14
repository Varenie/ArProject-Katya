package com.applaudostudios.karcore.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.applaudostudios.karcore.DataBase.Dao.ModelDao
import com.applaudostudios.karcore.DataBase.Entities.Model

@Database(
    entities = [Model::class],
    version = 3)
abstract class AppDatabase: RoomDatabase() {
    abstract fun modelDao(): ModelDao

    companion object{
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "myDB")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}