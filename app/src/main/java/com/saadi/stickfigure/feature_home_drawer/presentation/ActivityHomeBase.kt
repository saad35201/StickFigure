package com.saadi.stickfigure.feature_home_drawer.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
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

    //home nav
    private lateinit var mNavController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        //status bar config
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = false
        }
        setContentView(mBinding.root)

        //finding home nav
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment
        mNavController = navHostFragment.navController

        mBinding.apply {

            //setting toolbar and hamburger icon
            val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
            // Set the app bar title(default)
            supportActionBar?.title = getString(R.string.homepage)

            //setting homepage screen as default
            mBinding.navViewDrawer.setCheckedItem(R.id.nav_home_page)

            //nav click listener
            mBinding.navViewDrawer.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_my_profile -> {
                        supportActionBar?.title = getString(R.string.my_profile)
                        mNavController.navigate(R.id.fragmentProfile)
                    }
                    R.id.nav_home_page -> {
                        supportActionBar?.title = getString(R.string.homepage)
                        mNavController.navigate(R.id.fragmentHomePage)
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
                //closing drawer on item click
                mBinding.drawerLayout.closeDrawers()
                true
            }

            //cart btn click listener
            mBinding.fabCart.setOnClickListener {
                Toast.makeText(this@ActivityHomeBase,getString(R.string.cart), Toast.LENGTH_SHORT).show()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mBinding.drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
            R.id.menu_search -> {
                Toast.makeText(this,getString(R.string.search), Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_notification -> {
                Toast.makeText(this,getString(R.string.notification), Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}