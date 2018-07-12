package com.getto.cats.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.getto.cats.R
import com.getto.cats.data.entity.Cat
import com.getto.cats.presentation.main.view.MainView
import kotlinx.android.synthetic.main.cats_item.view.*

class CatsAdapter(private val cats: ArrayList<Cat>, private val view : MainView) : RecyclerView.Adapter<CatsAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){

        fun bindCats(cat : Cat){
            itemView.cat_name.text = cat.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cats_item, parent, false))
    }

    override fun getItemCount(): Int {
        return cats.count()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindCats(cats[position])
        holder.itemView.setOnClickListener(View.OnClickListener {
            view.showDetails(cats[position])
        })

    }

}