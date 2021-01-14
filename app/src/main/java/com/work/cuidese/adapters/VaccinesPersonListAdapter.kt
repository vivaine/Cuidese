package com.work.cuidese.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.work.cuidese.R
import com.work.cuidese.data.Vaccine
import com.work.cuidese.adapters.VaccinesPersonListAdapter.VaccinePersonViewHolder

class VaccinesPersonListAdapter(): ListAdapter<Vaccine, VaccinePersonViewHolder>(VaccinePersonDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccinesPersonListAdapter.VaccinePersonViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.vaccine_item, parent, false)
        return VaccinesPersonListAdapter.VaccinePersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: VaccinesPersonListAdapter.VaccinePersonViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class VaccinePersonViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val txtName: TextView = itemView.findViewById(R.id.txtVaccine)

        private var currentVaccine: Vaccine? = null

        fun bind(vaccine: Vaccine) {
            currentVaccine = vaccine
            txtName.text = vaccine.name
        }
    }
}

object VaccinePersonDiffCallback : DiffUtil.ItemCallback<Vaccine>() {
    override fun areItemsTheSame(oldItem: Vaccine, newItem: Vaccine): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Vaccine, newItem: Vaccine): Boolean {
        return (oldItem.name == newItem.name) &&
                (oldItem.ageMonths == newItem.ageMonths) &&
                (oldItem.gender == newItem.gender)
    }
}