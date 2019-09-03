package com.anggit97.cataloguemovie.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by Anggit Prayogo on 2019-08-27.
 * Github : @anggit97
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}