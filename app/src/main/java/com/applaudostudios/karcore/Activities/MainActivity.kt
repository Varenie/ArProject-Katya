package com.applaudostudios.karcore.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applaudostudios.karcore.Adapters.RecyclerAdapter
import com.applaudostudios.karcore.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler = findViewById<RecyclerView>(R.id.my_recycler)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)

        recycler.adapter = RecyclerAdapter()
    }

}
