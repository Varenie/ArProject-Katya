package com.applaudostudios.karcore.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.applaudostudios.karcore.DataBase.Dao.ModelDao
import com.applaudostudios.karcore.DataBase.Entities.Model
import com.applaudostudios.karcore.R
import com.applaudostudios.karcore.Singleton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RecyclerAdapter(
    private val modelList: List<Model>,
    private val context: Context,
    private val modelDao: ModelDao?
    ): RecyclerView.Adapter<RecyclerAdapter.VHolder>() {
    class VHolder(itemView: View, private val context: Context, private val modelDao: ModelDao?, modelList: List<Model>): RecyclerView.ViewHolder(itemView) {
        val photo = itemView.findViewById<ImageView>(R.id.iv_model_photo)
        val name = itemView.findViewById<TextView>(R.id.tv_model_name)
        val desc = itemView.findViewById<TextView>(R.id.tv_model_derscription)
        val favourite = itemView.findViewById<ImageView>(R.id.iv_like)

        init {
            itemView.setOnLongClickListener {
                val url = modelList[adapterPosition].modelUrl
                val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
                sceneViewerIntent.data =
                    Uri.parse("https://arvr.google.com/scene-viewer/1.0?file=$url")
                sceneViewerIntent.setPackage("com.google.android.googlequicksearchbox")
                context.startActivity(sceneViewerIntent)
                return@setOnLongClickListener true
            }
        }

        fun bind(model: Model) {
            val bmp = BitmapFactory.decodeByteArray(model.photo, 0, model.photo.size)
            photo.setImageBitmap(bmp)
            name.text = model.modelName
            desc.text = model.description

            if (model.isFavourite) {
                favourite.setImageResource(R.drawable.ic_favorite_fill)
            }

            favourite.setOnClickListener {
                if (model.isFavourite) {
                    val mModel = Model(
                        id = model.id,
                        modelName = model.modelName,
                        isFavourite = false,
                        description = model.description,
                        modelUrl = model.modelUrl,
                        photo = model.photo
                    )
                    updateFavourite(mModel)
                    Singleton.isFavouriteFlag.value = false
                    favourite.setImageResource(R.drawable.ic_favorite_border)

                } else {
                    val mModel = Model(
                        id = model.id,
                        modelName = model.modelName,
                        isFavourite = true,
                        description = model.description,
                        modelUrl = model.modelUrl,
                        photo = model.photo
                    )
                    updateFavourite(mModel)
                    Singleton.isFavouriteFlag.value = true
                    favourite.setImageResource(R.drawable.ic_favorite_fill)

                }
            }
        }

        private fun updateFavourite(mModel: Model) = GlobalScope.launch {
            modelDao?.updateModel(mModel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_item, parent, false)

        return VHolder(view, context, modelDao, modelList)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(modelList[position])
    }

    override fun getItemCount(): Int {
        return modelList.size
    }
}