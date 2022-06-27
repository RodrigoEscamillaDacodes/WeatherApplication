package com.dacodes.weatherapp.core.presentation

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VDB : ViewDataBinding>(
    @LayoutRes
    protected val layoutId: Int,
) : AppCompatActivity() {

    protected lateinit var binding: VDB
    protected val loader by lazy { LoaderDialog(this@BaseActivity) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
    }

}