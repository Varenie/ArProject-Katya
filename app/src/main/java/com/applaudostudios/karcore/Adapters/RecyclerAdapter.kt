package com.applaudostudios.karcore.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.applaudostudios.karcore.R

class RecyclerAdapter(): RecyclerView.Adapter<RecyclerAdapter.VHolder>() {
    class VHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int){}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_item, null)

        return VHolder(view)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 6
    }
}