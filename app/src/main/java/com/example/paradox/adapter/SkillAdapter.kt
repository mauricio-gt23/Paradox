package com.example.paradox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paradox.R
import com.example.paradox.models.Skill

class SkillAdapter(val skills: List<Skill>, val context: Context)
    : RecyclerView.Adapter<SkillPrototype>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillPrototype {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prototype_skills, parent, false)
        return SkillPrototype(view)
    }

    override fun onBindViewHolder(holder: SkillPrototype, position: Int) {
        holder.bind(skills[position])
    }

    override fun getItemCount(): Int {
        return skills.size
    }


}

class SkillPrototype(itemView: View) : RecyclerView.ViewHolder(itemView){
    val tvSkillName = itemView.findViewById<TextView>(R.id.tvSkillName)

    fun bind(skill: Skill) {
        tvSkillName.text = skill.name
    }
}