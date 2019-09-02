package com.anggit97.myweather

import android.util.Log.e
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anggit97.myweather.data.WeatherItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject


/**
 * Created by Anggit Prayogo on 2019-09-01.
 * Github : @anggit97
 */
class MainViewModel : ViewModel() {

    private val API_KEY = "827ccc94b9d80b6937cae986bc675e5e"
    val listItems: MutableList<WeatherItems> = mutableListOf()
    private var listWeathers: MutableLiveData<MutableList<WeatherItems>> = MutableLiveData()

    fun setWeather(cities: String) {
        val client = AsyncHttpClient()
        val listItem: MutableList<WeatherItems> = mutableListOf()
        val url =
            "https://api.openweathermap.org/data/2.5/group?id=$cities&units=metric&appid=$API_KEY"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                result?.let {
                    val responseObject = JSONObject(it)
                    val list = responseObject.getJSONArray("list")
                    var i = 0
                    while (i < list.length()) {
                        val weather = list.getJSONObject(i)
                        val weatherItems = WeatherItems(weather)
                        listItems.add(weatherItems)
                        i++
                    }
                    listWeathers.postValue(listItems)
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                e("ERROR ", error?.message ?: "null error")
            }
        })
    }

    fun getWeather(): LiveData<MutableList<WeatherItems>> = listWeathers
}