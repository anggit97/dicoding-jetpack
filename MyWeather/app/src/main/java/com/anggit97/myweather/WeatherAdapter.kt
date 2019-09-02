package com.anggit97.myweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggit97.myweather.data.WeatherItems
import kotlinx.android.synthetic.main.weather_items.view.*

/**
 * Created by Anggit Prayogo on 2019-09-01.
 * Github : @anggit97
 */
class WeatherAdapter: RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private var mData: MutableList<WeatherItems> = mutableListOf()

    fun setData(data: MutableList<WeatherItems>){
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bindView(weatherItems: WeatherItems) {
            with(itemView){
                textKota.text = weatherItems.name
                textDesc.text = weatherItems.description
                textTemp.text = weatherItems.temperature
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.weather_items, parent, false))
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(mData[position])
    }
}