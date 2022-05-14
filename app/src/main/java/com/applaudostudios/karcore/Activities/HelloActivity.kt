package com.applaudostudios.karcore.Activities

import android.content.Intent
import android.opengl.ETC1
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.applaudostudios.karcore.DataBase.AppDatabase
import com.applaudostudios.karcore.DataBase.Entities.Model
import com.applaudostudios.karcore.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HelloActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        val btnLib = findViewById<Button>(R.id.btn_lib)
        val btnAdd = findViewById<Button>(R.id.btn_add_model)

        btnLib.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        btnAdd.setOnClickListener {
            openDialog()
        }
    }

    private fun openDialog() {
        val alertDialog = AlertDialog.Builder(this)

        val inflater = LayoutInflater.from(this)
        val myWindow = inflater.inflate(R.layout.layout_add_model, null)
        alertDialog.setView(myWindow)

        val btnConfirm = myWindow.findViewById<Button>(R.id.btn_confirm)

        val dialog = alertDialog.show()

        btnConfirm.setOnClickListener {
            onConfirmClick(myWindow)
            dialog.dismiss()
        }
    }

    private fun onConfirmClick(myWindow: View) {
        val etLink = myWindow.findViewById<EditText>(R.id.et_model_link)
        val etName = myWindow.findViewById<EditText>(R.id.et_name)
        val etDescription = myWindow.findViewById<EditText>(R.id.et_description)

        val modelUrl = etLink.text.toString()
        val modelName = etName.text.toString()
        val modelDescription = etDescription.text.toString()

        val model = Model(
            modelName = modelName,
            isFavourite = false,
            modelUrl = modelUrl,
            photoUri = R.drawable.smalltable.toString()
        )

        addModel(model)
    }

    private fun addModel(model: Model) = GlobalScope.launch {
        val db = AppDatabase.getAppDataBase(this@HelloActivity)
        val modelDao = db?.modelDao()

        modelDao?.insertModel(model)
    }
}