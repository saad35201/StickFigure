package com.saadi.stickfigure.feature_home_drawer.presentation

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
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

        setupDrawerLayout()

        //cart btn click listener
        mBinding.fabCart.setOnClickListener {
            Toast.makeText(this@ActivityHomeBase, getString(R.string.cart), Toast.LENGTH_SHORT)
                .show()
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

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(mNavController, mBinding.drawerLayout)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                Toast.makeText(this, getString(R.string.search), Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_notification -> {
                Toast.makeText(this, getString(R.string.notification), Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupDrawerLayout() {
        val toolbar: androidx.appcompat.widget.Toolbar = mBinding.appBar.toolbar
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            mBinding.drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        mBinding.drawerLayout.addDrawerListener(toggle)
        toggle.isDrawerSlideAnimationEnabled = true
        toggle.isDrawerIndicatorEnabled = true
        toggle.syncState()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment
        mNavController = navHostFragment.navController
        mBinding.navViewDrawer.setupWithNavController(mNavController)

        NavigationUI.setupActionBarWithNavController(this, mNavController, mBinding.drawerLayout)

    }

    override fun onBackPressed() {
        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}