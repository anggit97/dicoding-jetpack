package com.anggit97.cataloguemovie.testing

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.anggit97.cataloguemovie.R
import com.anggit97.cataloguemovie.base.BaseActivity
import com.anggit97.cataloguemovie.ui.home.movie.MovieFragment
import com.anggit97.cataloguemovie.ui.home.tvshow.TvShowFragment
import kotlinx.android.synthetic.main.home_activity.*

/**
 * Created by Anggit Prayogo on 2019-08-27.
 * Github : @anggit97
 */
class SingleFragmentActivity: BaseActivity() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        initViewPager()
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