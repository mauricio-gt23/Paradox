package com.example.paradox.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.R
import com.example.paradox.models.Postulant

class PostulantAdapter(var postulants: ArrayList<Postulant>): RecyclerView.Adapter<PostulantPrototype>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostulantPrototype {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_element_postulant, parent, false)
        return PostulantPrototype(view)
    }

    override fun onBindViewHolder(holder: PostulantPrototype, position: Int) {
        holder.bind(postulants.get(position))
    }

    override fun getItemCount(): Int {
        return postulants.size
    }
}

class PostulantPrototype(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvPostulant = itemView.findViewById<TextView>(R.id.tvPostulant)
    fun bind(postulant: Postulant) {
        tvPostulant.text = postulant.name
    }
}
