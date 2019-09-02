package com.anggit97.mylivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        subscribe()
    }

    private fun subscribe() {
        val elapseTimeObserver = Observer<Long> {
            val newText = resources.getString(R.string.seconds, it)
            timer_textview.text = newText
        }

        mainViewModel.getElapseTime().observe(this, elapseTimeObserver)
    }
}
