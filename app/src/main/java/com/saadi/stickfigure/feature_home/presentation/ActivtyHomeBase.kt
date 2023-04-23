package com.saadi.stickfigure.feature_home.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saadi.stickfigure.R

class ActivtyHomeBase : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}