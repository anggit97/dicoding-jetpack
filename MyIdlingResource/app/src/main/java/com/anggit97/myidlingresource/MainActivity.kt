package com.anggit97.myidlingresource

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clickListener()
    }

    private fun clickListener() {
        button.setOnClickListener {
            delay1()
            delay2()
        }
    }

    private fun delay1() {
        EspressoIdlingResource.increment()
        Handler().postDelayed({
            text_view.text = resources.getString(R.string.delay1)

            if (!EspressoIdlingResource.espressoIdlingResource.isIdleNow){
                EspressoIdlingResource.decrement()
            }
        }, 2000L)
    }

    private fun delay2() {
        EspressoIdlingResource.increment()
        Handler().postDelayed({
            text_view.text = getString(R.string.delay2)
            if (!EspressoIdlingResource.espressoIdlingResource.isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        }, 2000L)
    }
}
