package com.saadi.stickfigure.feature_home_drawer.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import com.saadi.stickfigure.R
import com.saadi.stickfigure.databinding.ActivityHomeBinding
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.User
import com.saadi.stickfigure.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityHomeBase : AppCompatActivity() {

    private lateinit var mBinding : ActivityHomeBinding
    private val mVmHomeBase by viewModels<VmHomeBase>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        mBinding.apply {
            val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)

            //setting toolbar and hamburger icon
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

            //nav click listener
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

        //observingLiveData
        mVmHomeBase.getUser()
        observe(mVmHomeBase.userLiveData, ::handleUserName)

    }

    private fun handleUserName(user: User) {
        //setting username in drawer hearder
        mBinding.navViewDrawer.getHeaderView(0).findViewById<TextView>(R.id.tv_user_name).text = user.name
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            mBinding.drawerLayout.openDrawer(GravityCompat.START)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}