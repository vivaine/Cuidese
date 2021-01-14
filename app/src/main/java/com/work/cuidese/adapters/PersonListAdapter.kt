package com.work.cuidese.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.work.cuidese.R
import com.work.cuidese.data.Person
import com.work.cuidese.adapters.PersonListAdapter.PersonViewHolder

class PersonListAdapter(
        private val onClick: (Person) -> Unit) :
    ListAdapter<Person, PersonViewHolder> (PersonDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.person_item, parent, false)
        return PersonViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class PersonViewHolder(itemView: View, val onClick: (Person) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val txtName: TextView = itemView.findViewById(R.id.txtPerson)
        private var currentPerson: Person? = null

        init {

            itemView.setOnClickListener{
                currentPerson?.let {
                    onClick(it)
                }
            }
        }

        fun bind(person: Person) {
            currentPerson = person
            txtName.text = person.name
        }
    }
}

object PersonDiffCallback : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return (oldItem.name == newItem.name) && (oldItem.birth == newItem.birth)
    }
}