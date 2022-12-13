package com.cutecats.android.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cutecats.android.R
import com.cutecats.android.data.model.CatItem
import com.cutecats.android.databinding.ItemCatBinding

class CatsListAdapter(private var items: List<CatItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding =
            ItemCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CatItemHolder(
            binding
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var mainHolder = holder as CatItemHolder

        val catItem = items[position]

        Glide.with(mainHolder.binding.root.context)
            .load(catItem.url)
            .placeholder(R.drawable.placeholder_cat)
           // .crossFade()
            .into(mainHolder.binding.imgCat);


    }

    override fun getItemCount(): Int {
        return items.size
    }

    class CatItemHolder(val binding: ItemCatBinding) :
        RecyclerView.ViewHolder(binding.root) {}

}

