package com.example.paradox.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.R
import com.example.paradox.models.Work

class WorkAdapter(var works: ArrayList<Work>): RecyclerView.Adapter<WorkPrototype>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkPrototype {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_element_card_work, parent, false)
        return WorkPrototype(view)
    }

    override fun onBindViewHolder(holder: WorkPrototype, position: Int) {
        holder.bind(works.get(position))
    }

    override fun getItemCount(): Int {
        return works.size
    }
}

class WorkPrototype(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvCompany = itemView.findViewById<TextView>(R.id.tvCompany)
    val tvSubtitle = itemView.findViewById<TextView>(R.id.tvSubtitle)
    val tvJob = itemView.findViewById<TextView>(R.id.tvJob)
    val tvTime = itemView.findViewById<TextView>(R.id.tvTime)
    val tvInfo = itemView.findViewById<TextView>(R.id.tvInfo)

    fun bind(work: Work) {
        tvCompany.text = work.company
        tvSubtitle.text = work.subtitle
        tvJob.text = work.job
        tvTime.text = work.time
        tvInfo.text = work.info
    }
}
