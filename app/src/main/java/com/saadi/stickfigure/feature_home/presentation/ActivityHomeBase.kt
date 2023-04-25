package com.saadi.stickfigure.feature_home.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.saadi.stickfigure.R
import com.saadi.stickfigure.databinding.ActivityHomeBinding

class ActivityHomeBase : AppCompatActivity() {

    private lateinit var mBinding : ActivityHomeBinding
    private lateinit var mToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        mBinding.apply {
            val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)

            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

            mBinding.navViewDrawer.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_my_profile -> {
                        Toast.makeText(this@ActivityHomeBase, "My profile", Toast.LENGTH_SHORT).show()
                    }
                    R.id.nav_home_page -> {
                        Toast.makeText(this@ActivityHomeBase, "HomePage", Toast.LENGTH_SHORT).show()
                    }
                    R.id.nav_news_feed -> {
                        Toast.makeText(this@ActivityHomeBase, "NewsFeed", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            mBinding.drawerLayout.openDrawer(GravityCompat.START)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}