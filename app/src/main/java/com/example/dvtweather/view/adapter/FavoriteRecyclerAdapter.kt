package com.example.dvtweather.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dvtweather.R
import com.example.dvtweather.data.entity.favourite.FavoriteEntity
import com.example.dvtweather.util.AspectRatioImageView

class FavoriteRecyclerAdapter(
    private var itemList: MutableList<FavoriteEntity>,
    private var context: Context
) : RecyclerView.Adapter<FavoriteRecyclerAdapter.Holder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun replaceData(list: MutableList<FavoriteEntity>) {
        this.itemList = list
        notifyDataSetChanged()
    }

    class Holder private constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        private var currentLocation: TextView = view.findViewById(R.id.currentLocation)
        private var locationIcon: AspectRatioImageView = view.findViewById(R.id.locationIcon)
        private var locationTemp: TextView = view.findViewById(R.id.locationTemp)

        fun setData(data: FavoriteEntity, context: Context) {

            Glide.with(context).load(ContextCompat.getDrawable(context, data.icon))
                .into(locationIcon)
            locationTemp.text = data.temp
            currentLocation.text = data.name
        }


        companion object {
            fun create(parent: ViewGroup): Holder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.favorite_drawer_location, parent, false)
                return Holder(view)
            }
        }


    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder = Holder.create(parent)

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setData(itemList[position], context)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}