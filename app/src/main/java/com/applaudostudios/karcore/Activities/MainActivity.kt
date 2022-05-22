package com.applaudostudios.karcore.Activities

import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applaudostudios.karcore.Adapters.RecyclerAdapter
import com.applaudostudios.karcore.DataBase.AppDatabase
import com.applaudostudios.karcore.DataBase.Dao.ModelDao
import com.applaudostudios.karcore.DataBase.Entities.Model
import com.applaudostudios.karcore.R
import com.applaudostudios.karcore.Singleton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.Observable
import io.reactivex.Scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var db: AppDatabase? = null
    private var modelDao: ModelDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setDB()
        setObservables()
        setListeners()
    }

    private fun setObservables(){
        Singleton.modelList.observe(this) {
            setRecycler()
        }

        Singleton.isFavouriteFlag.observe(this) {
            Thread.sleep(100)
            getValueForList()
        }

        Singleton.isDeleteFlag.observe(this) {
            Thread.sleep(100)
            getValueForList()
        }
    }

    private fun setListeners() {
        val fabFavourite = findViewById<FloatingActionButton>(R.id.fab_favourites)
        val searchView = findViewById<SearchView>(R.id.searchView)

        fabFavourite.setOnClickListener{
            getFavouriteModelsForList()
        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    getModelByName(it)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun setDB() = GlobalScope.launch {
        db = AppDatabase.getAppDataBase(this@MainActivity)
        modelDao = db?.modelDao()

        getValueForList()
    }

    private fun setRecycler() {
        Singleton.modelList.value?.let {
            val recycler = findViewById<RecyclerView>(R.id.my_recycler)

            recycler.layoutManager = LinearLayoutManager(this)
            recycler.setHasFixedSize(true)

            recycler.adapter = RecyclerAdapter(it, this, modelDao)

        }
    }

    private fun getValueForList() = GlobalScope.launch{
        Singleton.modelList.postValue(modelDao?.getModels())
    }

    private fun getFavouriteModelsForList() = GlobalScope.launch{
        Singleton.modelList.postValue(modelDao?.getFavouriteModels())
    }

    private fun getModelByName(name: String) = GlobalScope.launch {
        Singleton.modelList.postValue(modelDao?.getModelByName(name))
    }

}
