package com.example.paradox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paradox.R

import com.example.paradox.models.Company


class CompanyAdapter(val companies: List<Company>, val context: Context, val itemClickListener: OnItemClickListener):
    RecyclerView.Adapter<CompanyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.prototype_company, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val company = companies[position]
        Glide.with(context).load(company.logo).into(holder.logo)
        holder.name.text = company.name
        holder.ruc.text = company.ruc.toString()

        holder.itemView.setOnClickListener { itemClickListener.onItemClicked(company.id) }
    }

    override fun getItemCount(): Int {
        return companies.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logo = itemView.findViewById<ImageView>(R.id.ivCardLogo)
        val name = itemView.findViewById<TextView>(R.id.tvCardName)
        val ruc = itemView.findViewById<TextView>(R.id.tvCardRuc)
    }

    interface OnItemClickListener {
        fun onItemClicked(id: Int)
    }
}