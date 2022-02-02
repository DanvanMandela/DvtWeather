package com.example.dvtweather.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dvtweather.R
import com.example.dvtweather.data.entity.weather.ForecastItem
import com.example.dvtweather.util.AspectRatioImageView

class ForecastRecyclerAdapter(
    private var itemList: MutableList<ForecastItem>,
    private var context: Context
) : RecyclerView.Adapter<ForecastRecyclerAdapter.Holder>() {


    @SuppressLint("NotifyDataSetChanged")
    fun replaceData(list: MutableList<ForecastItem>) {
        this.itemList = list
        notifyDataSetChanged()
    }

    class Holder private constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        private var day: TextView = view.findViewById(R.id.day)
        private var avatar: AspectRatioImageView = view.findViewById(R.id.avatar)
        private var temp: TextView = view.findViewById(R.id.temp)
        private var currentItem: LinearLayout = view.findViewById(R.id.currentItem)


        fun setData(forecastItem: ForecastItem, context: Context) {
            day.text = forecastItem.day
            Glide.with(context).load(ContextCompat.getDrawable(context, forecastItem.icon))
                .into(avatar)
            temp.text = forecastItem.temp
            //currentItem.setBackgroundColor(ContextCompat.getColor(context, forecastItem.color))

        }


        companion object {
            fun create(parent: ViewGroup): Holder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.weather_forecast_item, parent, false)
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