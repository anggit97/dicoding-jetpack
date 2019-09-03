package com.anggit97.cataloguemovie.ui.splash

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anggit97.cataloguemovie.ui.home.HomeActivity
import com.anggit97.cataloguemovie.R
import com.anggit97.cataloguemovie.base.BaseActivity
import com.anggit97.cataloguemovie.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class SplashScreenActivity : BaseActivity(), Animator.AnimatorListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lottieAnimationListener()

        changeStatusBar()
    }

    private fun changeStatusBar() {
        StatusBarUtil.changeStatusBarColor(this, R.color.colorWhite)
        StatusBarUtil.changeStatusBarItemColor(this, true)
    }

    private fun lottieAnimationListener() {
        lottie_movie.addAnimatorListener(this)
    }

    override fun onAnimationRepeat(p0: Animator?) {

    }

    override fun onAnimationEnd(p0: Animator?) {
        startActivity(intentFor<HomeActivity>().clearTask().newTask())
    }

    override fun onAnimationCancel(p0: Animator?) {
    }


    override fun onAnimationStart(p0: Animator?) {

    }
}
