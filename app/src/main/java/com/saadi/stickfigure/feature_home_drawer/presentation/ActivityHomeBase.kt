package com.saadi.stickfigure.feature_home_drawer.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowInsetsController
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.saadi.stickfigure.R
import com.saadi.stickfigure.databinding.ActivityHomeBinding
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.User
import com.saadi.stickfigure.feature_auth.presentation.ActivityAuthBase
import com.saadi.stickfigure.utils.loadImageProfile
import com.saadi.stickfigure.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityHomeBase : AppCompatActivity() {

    private lateinit var mBinding: ActivityHomeBinding
    private val mVmHomeBase by viewModels<VmHomeBase>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        //changing status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        setContentView(mBinding.root)


        mBinding.apply {
            val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)

            //setting toolbar and hamburger icon
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

            //nav click listener
            mBinding.navViewDrawer.setNavigationItemSelectedListener {
                //closing drawer on item click
                mBinding.drawerLayout.closeDrawers()
                when (it.itemId) {
                    R.id.nav_my_profile -> {
                        supportActionBar?.title = getString(R.string.my_profile)
                    }
                    R.id.nav_home_page -> {
                        supportActionBar?.title = getString(R.string.homepage)
                    }
                    R.id.nav_news_feed -> {
                        supportActionBar?.title = getString(R.string.newsfeed)
                    }
                    R.id.nav_creator -> {
                        supportActionBar?.title = getString(R.string.creator)
                    }
                    R.id.nav_shop -> {
                        supportActionBar?.title = getString(R.string.shop)
                    }
                    R.id.nav_messages -> {
                        supportActionBar?.title = getString(R.string.messages)
                    }
                    R.id.nav_my_store -> {
                        supportActionBar?.title = getString(R.string.my_store)
                    }
                    R.id.nav_wallet -> {
                        supportActionBar?.title = getString(R.string.wallet)
                    }
                    R.id.nav_setting -> {
                        supportActionBar?.title = getString(R.string.settings)
                    }
                }
                true
            }

            //logout click listener
            mBinding.navViewDrawer.findViewById<TextView>(R.id.tv_logout).setOnClickListener {
                mVmHomeBase.clearDataStore()
                mBinding.drawerLayout.closeDrawers()
                startActivity(
                    Intent(this@ActivityHomeBase, ActivityAuthBase::class.java)
                        .putExtra("destinationFragment", R.id.fragmentSignIn)
                )
                this@ActivityHomeBase.finish()
            }
        }

        //observingLiveData
        mVmHomeBase.getUser()
        observe(mVmHomeBase.userLiveData, ::handleUserData)

    }

    private fun handleUserData(user: User) {
        //setting username in drawer header
        mBinding.navViewDrawer.getHeaderView(0).findViewById<TextView>(R.id.tv_user_name).text =
            user.name
        mBinding.navViewDrawer.getHeaderView(0).findViewById<ImageView>(R.id.iv_user_profile)
            .loadImageProfile(
                user.photo as String
            )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            mBinding.drawerLayout.openDrawer(GravityCompat.START)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}