package com.example.paradox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.R
import com.example.paradox.models.Language

class LanguageAdapter(val languages: List<Language>, val context: Context)
    : RecyclerView.Adapter<LanguagePrototype>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguagePrototype {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prototype_languages, parent, false)
        return LanguagePrototype(view)
    }

    override fun onBindViewHolder(holder: LanguagePrototype, position: Int) {
        holder.bind(languages[position])
    }

    override fun getItemCount(): Int {
        return languages.size
    }


}

class LanguagePrototype(itemView: View) : RecyclerView.ViewHolder(itemView){
    val tvLanguageName = itemView.findViewById<TextView>(R.id.tvLanguageName)
    val tvLanguageLevel = itemView.findViewById<TextView>(R.id.tvLanguageLevel)

    fun bind(language: Language) {
        tvLanguageName.text = language.name
        if (language.level == 1) {
            tvLanguageLevel.text = "Básico"
        } else if (language.level == 2) {
            tvLanguageLevel.text = "Intermedio"
        } else if (language.level == 3) {
            tvLanguageLevel.text = "Avanzado"
        }
    }
}