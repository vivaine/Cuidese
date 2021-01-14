package com.work.cuidese.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.work.cuidese.R
import com.work.cuidese.data.Person
import com.work.cuidese.data.Vaccine
import com.work.cuidese.adapters.VaccineListAdapter.VaccineViewHolder

class VaccineListAdapter(private val onClick: (Vaccine) -> Unit):
    ListAdapter<Vaccine, VaccineViewHolder> (VaccineDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccineViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.vaccine_item, parent, false)
        return VaccineViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: VaccineViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class VaccineViewHolder(itemView: View, val onClick: (Vaccine) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val txtName: TextView = itemView.findViewById(R.id.txtVaccine)

        private var currentVaccine: Vaccine? = null

        init {
            itemView.setOnClickListener{
                currentVaccine?.let {
                    onClick(it)
                }
            }
        }

        fun bind(vaccine: Vaccine) {
            currentVaccine = vaccine
            txtName.text = vaccine.name
        }
    }
}

object VaccineDiffCallback : DiffUtil.ItemCallback<Vaccine>() {
    override fun areItemsTheSame(oldItem: Vaccine, newItem: Vaccine): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Vaccine, newItem: Vaccine): Boolean {
        return (oldItem.name == newItem.name) &&
                (oldItem.ageMonths == newItem.ageMonths) &&
                (oldItem.gender == newItem.gender)
    }
}