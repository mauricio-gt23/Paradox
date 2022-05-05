package com.example.paradox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostulationAdapter(var works: ArrayList<Work>): RecyclerView.Adapter<PostulationPrototype>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostulationPrototype {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_element_postulation, parent, false)
        return PostulationPrototype(view)
    }
    override fun onBindViewHolder(holder: PostulationPrototype, position: Int) {
        holder.bind(works.get(position))
    }
    override fun getItemCount(): Int {
        return works.size
    }
}

class PostulationPrototype(itemView: View): RecyclerView.ViewHolder(itemView) {
    val tvCompany = itemView.findViewById<TextView>(R.id.tvCompany)
    val tvSubtitle = itemView.findViewById<TextView>(R.id.tvSubtitle)
    val tvJob = itemView.findViewById<TextView>(R.id.tvJob)
    val tvTime = itemView.findViewById<TextView>(R.id.tvTime)
    val tvStatus = itemView.findViewById<TextView>(R.id.tvStatus)

    fun bind(work: Work) {
        tvCompany.text = work.company
        tvSubtitle.text = work.subtitle
        tvJob.text = work.job
        tvTime.text = work.time
        tvStatus.text = work.status
    }
}
