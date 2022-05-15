package com.applaudostudios.karcore.Activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.ETC1
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.applaudostudios.karcore.DataBase.AppDatabase
import com.applaudostudios.karcore.DataBase.Entities.Model
import com.applaudostudios.karcore.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class HelloActivity : AppCompatActivity() {
    private val REQUEST_CODE_GALLERY = 11
    private val PERMISSION_ID = 101

    var byteArray: ByteArray? = null

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

        if (!checkPermission()) {
            requestPermission()
        }
    }

    private fun openDialog() {
        val alertDialog = AlertDialog.Builder(this)

        val inflater = LayoutInflater.from(this)
        val myWindow = inflater.inflate(R.layout.layout_add_model, null)
        alertDialog.setView(myWindow)

        val btnConfirm = myWindow.findViewById<Button>(R.id.btn_confirm)
        val btnPhoto = myWindow.findViewById<ImageView>(R.id.iv_add_photo)

        val dialog = alertDialog.show()

        btnPhoto.setOnClickListener {
            openGalelleryForImage()
        }
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


        val bm = BitmapFactory.decodeResource(resources, R.drawable.lamp_thumb)
        val stream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val photo = stream.toByteArray()

        val model = Model(
            modelName = modelName,
            isFavourite = false,
            description = modelDescription,
            modelUrl = modelUrl,
            photo = byteArray ?: photo
        )

        addModel(model)
    }

    private fun addModel(model: Model) = GlobalScope.launch {
        val db = AppDatabase.getAppDataBase(this@HelloActivity)
        val modelDao = db?.modelDao()

        modelDao?.insertModel(model)
    }

    private fun openGalelleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                REQUEST_CODE_GALLERY -> {
                    val photoUri = data.data
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    byteArray = stream.toByteArray()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
            PERMISSION_ID
        )
    }
}