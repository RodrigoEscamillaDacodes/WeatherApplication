package com.dacodes.weatherapp.presentation

import android.os.Bundle
import com.dacodes.weatherapp.R
import com.dacodes.weatherapp.core.presentation.BaseActivity
import com.dacodes.weatherapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}