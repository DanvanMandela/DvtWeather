package com.example.dvtweather.view.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dvtweather.R
import com.example.dvtweather.data.entity.favourite.FavoriteEntity
import com.example.dvtweather.data.entity.favourite.FavoriteItem
import com.example.dvtweather.view.activity.MainActivity
import com.google.android.material.card.MaterialCardView
import org.ocpsoft.prettytime.PrettyTime
import android.location.Geocoder


class FavoriteRecyclerViewMain(
    private var itemList: MutableList<FavoriteItem>,
    private var activity: Activity
) : RecyclerView.Adapter<FavoriteRecyclerViewMain.Holder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun replaceData(list: MutableList<FavoriteItem>) {
        this.itemList = list
        notifyDataSetChanged()
    }

    class Holder private constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        private val placeName: TextView = view.findViewById(R.id.placeName)
        private val placeTemp: TextView = view.findViewById(R.id.placeTemp)
        private val countryName: TextView = view.findViewById(R.id.countryName)
        private val timeDate: TextView = view.findViewById(R.id.timeDate)
        private val weatherType: TextView = view.findViewById(R.id.weatherType)
        private var currentItem: MaterialCardView = view.findViewById(R.id.currentItem)

        fun setData(data: FavoriteItem, activity: Activity) {
            placeName.text = data.favoriteEntity.name
            placeTemp.text = data.favoriteEntity.temp
            timeDate.text = PrettyTime().format(data.favoriteEntity.date)
            weatherType.text = activity.getString(data.favoriteEntity.weather)
            currentItem.setOnClickListener {
                (activity as MainActivity)
                    .provideNavigationGraph()
                    .navigate(data.direction)
            }

        }

        companion object {
            fun create(parent: ViewGroup): Holder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.favorite_location_main, parent, false)
                return Holder(view)
            }
        }


    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder = Holder.create(parent)

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setData(itemList[position], activity)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}