package com.example.paradox.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.R
import com.example.paradox.models.PostulantJob

class PostulationAdapter(var postulationJobs: List<PostulantJob>): RecyclerView.Adapter<PostulationPrototype>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostulationPrototype {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_element_postulation, parent, false)
        return PostulationPrototype(view)
    }
    override fun onBindViewHolder(holder: PostulationPrototype, position: Int) {
        holder.bind(postulationJobs.get(position))
    }
    override fun getItemCount(): Int {
        return postulationJobs.size
    }
}

class PostulationPrototype(itemView: View): RecyclerView.ViewHolder(itemView) {
    val tvCompany = itemView.findViewById<TextView>(R.id.tvCompany)
    val tvSubtitle = itemView.findViewById<TextView>(R.id.tvSubtitle)
    val tvJob = itemView.findViewById<TextView>(R.id.tvJob)
    val tvTime = itemView.findViewById<TextView>(R.id.tvTime)
    val tvStatus = itemView.findViewById<TextView>(R.id.tvStatus)

    fun bind(postulationJob: PostulantJob) {
        tvCompany.text = postulationJob.company
        tvSubtitle.text = postulationJob.subtitle
        tvJob.text = postulationJob.job
        tvTime.text = postulationJob.time
        if (postulationJob.status == true) {
            tvStatus.text = "ACEPTADO"
            tvStatus.setTextColor(Color.parseColor("#1E8903"))
        } else {
            tvStatus.text = "RECHAZADO"
            tvStatus.setTextColor(Color.parseColor("#FF1212"))
        }
    }
}
