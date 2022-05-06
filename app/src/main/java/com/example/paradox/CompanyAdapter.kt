package com.example.paradox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.prototype_company.view.*


class CompanyAdapter(val companies: List<Company>, val itemClickListener: OnItemClickListener):
    RecyclerView.Adapter<CompanyPrototype>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyPrototype {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.prototype_company, parent,false)

        return CompanyPrototype(view)
    }

    override fun onBindViewHolder(holder: CompanyPrototype, position: Int) {
        holder.bind(companies[position], itemClickListener)
    }

    override fun getItemCount(): Int {
        return companies.size
    }
}

class CompanyPrototype(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvCardName = itemView.tvCardName
    val tvCardRuc = itemView.tvCardRuc
    val ivCardLogo = itemView.ivCardLogo
    val cvCompany = itemView.cvCompany

    fun bind(company: Company, itemClickListener: OnItemClickListener) {
        tvCardName.text = company.name
        tvCardRuc.text = company.ruc
//        Glide.with(itemView).load(company.logo).into(ivCardLogo)
        cvCompany.setOnClickListener {
            itemClickListener.OnItemClicked(company)
        }
    }
}

interface OnItemClickListener {
    fun OnItemClicked(company: Company)
}