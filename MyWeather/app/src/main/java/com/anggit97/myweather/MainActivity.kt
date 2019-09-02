package com.anggit97.myweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggit97.myweather.data.WeatherItems
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var adapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.getWeather().observe(this, getWeather())

        adapter = WeatherAdapter()
        adapter.notifyDataSetChanged()

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter


        onListener()
    }

    private fun onListener() {
        val city = editCity.text.toString()

        if (TextUtils.isEmpty(city)) return

        mainViewModel.setWeather(city)
        showLoading(true)
    }

    private fun getWeather() = Observer<MutableList<WeatherItems>> {
        it?.let {
            adapter.setData(it)
            showLoading(false)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}
