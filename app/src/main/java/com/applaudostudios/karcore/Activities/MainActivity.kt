package com.applaudostudios.karcore.Activities

import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applaudostudios.karcore.Adapters.RecyclerAdapter
import com.applaudostudios.karcore.DataBase.AppDatabase
import com.applaudostudios.karcore.DataBase.Dao.ModelDao
import com.applaudostudios.karcore.DataBase.Entities.Model
import com.applaudostudios.karcore.R
import io.reactivex.Observable
import io.reactivex.Scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var db: AppDatabase? = null
    private var modelDao: ModelDao? = null

    val modelList = MutableLiveData<List<Model>?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setDB()
        setObservables()
    }

    private fun setObservables(){
        modelList.observe(this) {
            setRecycler()
        }
    }

    private fun setDB() = GlobalScope.launch {
        db = AppDatabase.getAppDataBase(this@MainActivity)
        modelDao = db?.modelDao()

        val lamp = Model(
            modelName = "Lamp",
            modelUri = Uri.parse("LampPost.sfb").toString(),
            photoUri = R.drawable.lamp_thumb.toString()
        )

        val table = Model(
            modelName = "Table",
            modelUri = Uri.parse("model.sfb").toString(),
            photoUri = R.drawable.smalltable.toString()
        )

        with(modelDao) {
            if (this?.getModels()?.size!! == 0) {
                this.insertModel(lamp)
                this.insertModel(table)
            }
        }

        modelList.postValue(db?.modelDao()?.getModels())
    }

    private fun setRecycler() {
        modelList.value?.let {
            val recycler = findViewById<RecyclerView>(R.id.my_recycler)

            recycler.layoutManager = LinearLayoutManager(this)
            recycler.setHasFixedSize(true)

            recycler.adapter = RecyclerAdapter(it, this)

        }
    }



}
