package com.applaudostudios.karcore.Adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.applaudostudios.karcore.DataBase.Entities.Model
import com.applaudostudios.karcore.R


class RecyclerAdapter(
    private val modelList: List<Model>,
    private val context: Context
    ): RecyclerView.Adapter<RecyclerAdapter.VHolder>() {
    class VHolder(itemView: View, private val context: Context): RecyclerView.ViewHolder(itemView) {
        val photo = itemView.findViewById<ImageView>(R.id.iv_model_photo)
        val name = itemView.findViewById<TextView>(R.id.tv_model_name)
        val desc = itemView.findViewById<TextView>(R.id.tv_model_derscription)

        init {
            itemView.setOnClickListener {
//                context.startActivity(Intent(context, PreviewActivity::class.java))
                val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
                sceneViewerIntent.data =
                    Uri.parse("https://arvr.google.com/scene-viewer/1.0?file=https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Avocado/glTF/Avocado.gltf")
                sceneViewerIntent.setPackage("com.google.android.googlequicksearchbox")
                context.startActivity(sceneViewerIntent)
            }
        }
        fun bind(model: Model) {
            photo.setImageDrawable(ContextCompat.getDrawable(context.applicationContext, model.photoUri.toInt()))
            name.text = model.modelName
            desc.text = "${model.modelUri}\n${model.photoUri}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_item, null)

        return VHolder(view, context)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(modelList[position])
    }

    override fun getItemCount(): Int {
        return modelList.size
    }
}