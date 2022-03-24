package com.applaudostudios.karcore.Adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
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