package com.example.paradox.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.R
import com.example.paradox.models.PublishedWork
import java.util.*

class PublishedWorkAdapter(var publishedWorks: List<PublishedWork>, val itemClickListener: OnItemClickListener<PublishedWork>): RecyclerView.Adapter<PublishedWorkPrototype>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublishedWorkPrototype {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_element_published_work, parent, false)
        return PublishedWorkPrototype(view)
    }

    override fun onBindViewHolder(holder: PublishedWorkPrototype, position: Int) {
        holder.bind(publishedWorks.get(position), itemClickListener)
    }

    override fun getItemCount(): Int {
        return publishedWorks.size
    }

}

class PublishedWorkPrototype(itemView: View): RecyclerView.ViewHolder(itemView) {

    val cvPublishedWork = itemView.findViewById<CardView>(R.id.cvPublishedWork)
    val tvJob = itemView.findViewById<TextView>(R.id.tvJob2)
    val tvTime = itemView.findViewById<TextView>(R.id.tvTime2)
    val tvSubtitle = itemView.findViewById<TextView>(R.id.tvSubtitle2)
    val tvAddress = itemView.findViewById<TextView>(R.id.tvAddress2)
    // TODO: COUNT POSTULANTS

    fun bind(publishedWork: PublishedWork, itemClickListener: OnItemClickListener<PublishedWork>?) {
        tvJob.text = publishedWork.job
        tvTime.text = publishedWork.time
        tvSubtitle.text = publishedWork.subtitle
        tvAddress.text = publishedWork.address

        // Capturar el PW que se dio click
        cvPublishedWork.setOnClickListener {
            itemClickListener!!.OnItemClicked(publishedWork)
        }
    }
}

interface OnItemClickListener<T>{
    fun OnItemClicked(obj: T)
}