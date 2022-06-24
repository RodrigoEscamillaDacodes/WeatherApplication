package com.dacodes.weatherapp.core.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView

abstract class BaseActivity : AppCompatActivity(){
    abstract fun layoutId(): Int
    abstract val fragmentContainer: FragmentContainerView

}