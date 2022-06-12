package com.example.paradox.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.R
import com.example.paradox.models.Work
import java.util.*
import kotlin.collections.ArrayList

class WorkAdapter(var works: ArrayList<Work>): RecyclerView.Adapter<WorkPrototype>(), Filterable {

    var workFilterList = ArrayList<Work>()

    init {
        workFilterList = works
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkPrototype {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_element_card_work, parent, false)
        return WorkPrototype(view)
    }

    override fun onBindViewHolder(holder: WorkPrototype, position: Int) {
        holder.bind(workFilterList.get(position))
    }

    override fun getItemCount(): Int {
        return workFilterList.size
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    workFilterList = works
                } else {
                    val resultList = ArrayList<Work>()
                    for (row in works) {
                        if (row.job.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT), ignoreCase = true)) {
                            resultList.add(row)
                        }
                    }
                    workFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = workFilterList
                return filterResults
            }

            @Suppress("UNCHECLED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                workFilterList = results?.values as ArrayList<Work>
                notifyDataSetChanged()
            }
        }
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
