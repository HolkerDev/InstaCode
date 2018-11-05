package com.example.holker.instacode

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.navigation_feed -> {
                loadFragment(HomeFragment())
                return true
            }
            R.id.navigation_contacs -> {
                loadFragment(ContactsFragment())
                return true
            }
            R.id.navigation_more -> {
                loadFragment(MoreFragment())
                return true
            }
        }
        return false
    }


    fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.fragmemt_container, fragment).commit()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navigation_bottom.setOnNavigationItemSelectedListener(this)
        loadFragment(HomeFragment())
    }
}
