package com.example.paradox.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.R
import com.example.paradox.models.PublishedWork
import com.example.paradox.models.PublishedWorks
import com.example.paradox.models.Work
import java.util.*
import kotlin.collections.ArrayList

class PublishedWorkAdapter(var publishedWorks: ArrayList<PublishedWork>, val itemClickListener: OnItemClickListener<PublishedWork>): RecyclerView.Adapter<PublishedWorkPrototype>(), Filterable {

    var publishedWorksFilterList = ArrayList<PublishedWork>()

    init {
        publishedWorksFilterList = publishedWorks
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublishedWorkPrototype {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_element_published_work, parent, false)
        return PublishedWorkPrototype(view)
    }

    override fun onBindViewHolder(holder: PublishedWorkPrototype, position: Int) {
        holder.bind(publishedWorksFilterList.get(position), itemClickListener)
    }

    override fun getItemCount(): Int {
        return publishedWorksFilterList.size
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    publishedWorksFilterList = publishedWorks
                } else {
                    val resultList = ArrayList<PublishedWork>()
                    for (row in publishedWorks) {
                        if (row.job.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT), ignoreCase = true)) {
                            resultList.add(row)
                        }
                    }
                    publishedWorksFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = publishedWorksFilterList
                return filterResults
            }

            @Suppress("UNCHECLED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                publishedWorksFilterList = results?.values as ArrayList<PublishedWork>
                notifyDataSetChanged()
            }
        }
    }
}



class PublishedWorkPrototype(itemView: View): RecyclerView.ViewHolder(itemView) {

    val cvPublishedWork = itemView.findViewById<CardView>(R.id.cvPublishedWork)
    val tvJob = itemView.findViewById<TextView>(R.id.tvJob2)
    val tvTime = itemView.findViewById<TextView>(R.id.tvTime2)
    val tvSubtitle = itemView.findViewById<TextView>(R.id.tvSubtitle2)
    val tvAddress = itemView.findViewById<TextView>(R.id.tvAddress2)

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