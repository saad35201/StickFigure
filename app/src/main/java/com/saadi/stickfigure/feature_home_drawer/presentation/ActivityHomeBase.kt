package com.saadi.stickfigure.feature_home_drawer.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.saadi.stickfigure.R
import com.saadi.stickfigure.databinding.ActivityHomeBinding
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.User
import com.saadi.stickfigure.feature_auth.presentation.ActivityAuthBase
import com.saadi.stickfigure.utils.loadImageProfile
import com.saadi.stickfigure.utils.observe
import com.saadi.stickfigure.utils.startActivity
import com.saadi.stickfigure.utils.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityHomeBase : AppCompatActivity() {

    private lateinit var mBinding: ActivityHomeBinding
    private val mVmHomeBase by viewModels<VmHomeBase>()

    //home nav
    private lateinit var mNavController: NavController
    private lateinit var mAppBarConfiguration: AppBarConfiguration

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

        //drawer config
        setupDrawer()

        //cart btn click listener
        mBinding.fabCart.setOnClickListener {
            this.toast(getString(R.string.cart))
        }

        //logout click listener
        mBinding.navViewDrawer.findViewById<TextView>(R.id.tv_logout).setOnClickListener {
            mVmHomeBase.clearDataStore()
            mBinding.drawerLayout.closeDrawers()
            this.startActivity(ActivityAuthBase::class.java, true) {
                putExtra("destinationFragment", R.id.fragmentSignIn)
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

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val currentDestination = mNavController.currentDestination?.id

        val hideMenuFor = listOf(
            R.id.fragmentBlogDetail,
            R.id.fragmentEditProfile,
        )

        menu?.let {
            for (index in 0 until menu.size()) {
                val item = menu.getItem(index)
                item.isVisible = !hideMenuFor.contains(currentDestination)
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                this.toast(getString(R.string.search))
                true
            }

            R.id.action_notification -> {
                this.toast(getString(R.string.notification))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupDrawer() {
        val toolbar: androidx.appcompat.widget.Toolbar = mBinding.appBar.toolbar
        setSupportActionBar(toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment
        mNavController = navHostFragment.navController

        //setting top level fragment to maintain menu icon for these fragments
        mAppBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragmentProfile,
                R.id.fragmentHomePage,
                R.id.fragmentNewsFeed,
                R.id.fragmentCreator,
                R.id.fragmentShop,
                R.id.fragmentBlog,
                R.id.fragmentMessage,
                R.id.fragmentMyStore,
                R.id.fragmentWallet,
                R.id.fragmentSetting
            ), mBinding.drawerLayout
        )

        setupActionBarWithNavController(mNavController, mAppBarConfiguration)
        mBinding.navViewDrawer.setupWithNavController(mNavController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp(mAppBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}