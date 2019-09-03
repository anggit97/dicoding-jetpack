package com.anggit97.cataloguemovie.ui.home

import android.os.Bundle
import android.text.Layout
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.anggit97.cataloguemovie.R
import com.anggit97.cataloguemovie.base.BaseActivity
import com.anggit97.cataloguemovie.ui.home.movie.MovieFragment
import com.anggit97.cataloguemovie.ui.home.tvshow.TvShowFragment
import com.anggit97.cataloguemovie.utils.StatusBarUtil
import kotlinx.android.synthetic.main.home_activity.*
import java.util.ArrayList

class HomeActivity : BaseActivity() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        initToolbar()

        initViewPager()

        initStatusBarPref()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.app_name)
        toolbar.textAlignment = View.TEXT_ALIGNMENT_CENTER
    }

    private fun initStatusBarPref() {
        StatusBarUtil.changeStatusBarColor(this, R.color.colorWhite)
        StatusBarUtil.changeStatusBarItemColor(this, true)
    }

    private fun initViewPager() {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(MovieFragment.newInstance(), getString(R.string.movie_title))
        viewPagerAdapter.addFragment(
            TvShowFragment.newInstance(),
            getString(R.string.tv_show_title)
        )
        view_pager.adapter = viewPagerAdapter
        tab_layout.setupWithViewPager(view_pager)
    }


    inner class ViewPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {

        private var mFragmentList = arrayListOf<Fragment>()
        private var mFragmentTitleList = arrayListOf<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int) = mFragmentTitleList.get(position)
    }
}
