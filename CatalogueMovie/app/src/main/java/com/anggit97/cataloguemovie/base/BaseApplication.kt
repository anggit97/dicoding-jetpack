package com.anggit97.cataloguemovie.base

import android.app.Application
import com.anggit97.cataloguemovie.R
import com.anggit97.cataloguemovie.config.FontConfig
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * Created by Anggit Prayogo on 2019-08-27.
 * Github : @anggit97
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initFont()
    }

    private fun initFont() {
        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath(FontConfig.FONT_DEFAULT)
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }
}