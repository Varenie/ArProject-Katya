package com.applaudostudios.karcore

import androidx.lifecycle.MutableLiveData
import com.applaudostudios.karcore.DataBase.Entities.Model

object Singleton {
    val modelList = MutableLiveData<List<Model>?>(arrayListOf())
    val isFavouriteFlag = MutableLiveData(false)
    val isDeleteFlag = MutableLiveData(false)
}