package com.example.paradox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paradox.R
import com.example.paradox.models.Company

class CompanyAdapter(val companies: List<Company>, val context: Context, val itemClickListener: OnItemClickListener<Company>):
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.prototype_company, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(companies.get(position), itemClickListener)
    }

    override fun getItemCount(): Int {
        return companies.size
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cvCompany = itemView.findViewById<CardView>(R.id.cvCompany)
    val logo = itemView.findViewById<ImageView>(R.id.ivCardLogo)
    val name = itemView.findViewById<TextView>(R.id.tvCardName)
    val ruc = itemView.findViewById<TextView>(R.id.tvCardRuc)

    fun bind(company: Company, itemClickListener: OnItemClickListener<Company>?) {
        Glide.with(itemView.context).load(company.logo).into(logo)
        name.text = company.name
        ruc.text = company.ruc.toString()

        cvCompany.setOnClickListener {
            itemClickListener!!.OnItemClicked(company)
        }
    }
}

//interface OnItemClickListener<Company> {
//    fun OnItemClicked(company: Company)
//}