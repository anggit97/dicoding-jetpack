package com.anggit97.academy.home

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.anggit97.academy.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*
import com.anggit97.academy.bookmark.BookmarkFragment
import com.anggit97.academy.academy.AcademyFragment


class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val SELECTED_MENU = "selected_menu"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        nav_view.setOnNavigationItemSelectedListener(this)
        if (savedInstanceState != null) {
            savedInstanceState.getInt(SELECTED_MENU)
        } else {
            nav_view.selectedItemId = R.id.action_home
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_MENU, nav_view.selectedItemId)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        if (item.itemId == R.id.action_home) {
            fragment = AcademyFragment.newInstance()
        } else if (item.itemId == R.id.action_bookmark) {
            fragment = BookmarkFragment.newInstance()
        }

        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, fragment)
                .commit()
        }
        return true
    }
}
