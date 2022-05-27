package com.example.paradox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.R
import com.example.paradox.models.Language
import com.example.paradox.models.Study

class StudyAdapter(val studies: List<Study>, val context: Context)
    : RecyclerView.Adapter<StudyPrototype>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyPrototype {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prototype_languages, parent, false)
        return StudyPrototype(view)
    }

    override fun onBindViewHolder(holder: StudyPrototype, position: Int) {
        holder.bind(studies[position])
    }

    override fun getItemCount(): Int {
        return studies.size
    }


}

class StudyPrototype(itemView: View) : RecyclerView.ViewHolder(itemView){
    val tvStudyName = itemView.findViewById<TextView>(R.id.tvStudyName)
    val tvStudyDegree = itemView.findViewById<TextView>(R.id.tvStudyDegree)

    fun bind(study: Study) {
        tvStudyName.text = study.name
        tvStudyDegree.text = study.degree.toString()
    }
}