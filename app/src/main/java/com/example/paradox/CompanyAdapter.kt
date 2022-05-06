package com.example.paradox

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

class CompanyAdapter (val companies: List<Company>, val context: Context, val itemClickListener: OnItemClickListener): RecyclerView.Adapter<CompanyAdapter.ViewHolder>() {
    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val ivCardLogo = view.findViewById<ImageView>(R.id.ivCardLogo)
        val tvCardName = view.findViewById<TextView>(R.id.tvCardName)
        val tvCardRuc = view.findViewById<TextView>(R.id.tvCardRuc)
        val cvCompany = view.findViewById<CardView>(R.id.cvCompany)
    }

    interface OnItemClickListener {
        fun onItemClicked(contact: Company)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyAdapter.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.prototype_company, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompanyAdapter.ViewHolder, position: Int) {
        val company = companies[position]

        holder.tvCardName.text = company.name
        holder.tvCardRuc.text = company.ruc.toString()

        val picBuilder = Picasso.Builder(context)
        picBuilder.downloader(OkHttp3Downloader(context))
        picBuilder.build().load(company.logo).into(holder.ivCardLogo)

        holder.cvCompany.setOnClickListener {
            itemClickListener.onItemClicked(company)
        }
    }

    override fun getItemCount(): Int {
        return companies.size
    }
}