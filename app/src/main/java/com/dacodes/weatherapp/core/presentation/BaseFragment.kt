package com.dacodes.weatherapp.core.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VDB : ViewDataBinding>(
    @LayoutRes
    protected val layoutId: Int,
) : Fragment(){

    protected lateinit var binding: VDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    protected fun <R> handleViewModelResult(
        result: ViewModelResult<R>,
        onSuccess: (R) -> Unit = { _ -> },
        onError: (String, Exception?) -> Unit = { _, _ -> },
        toggleLoading: (Boolean) -> Unit = { _ -> },
    ) {
        toggleLoading(
            result is ViewModelResult.Loading || result is ViewModelResult.Cache
        )
        when (result) {
            is ViewModelResult.Success -> {
                onSuccess(result.data)
            }
            is ViewModelResult.Cache -> {
                onSuccess(result.data)
            }
            is ViewModelResult.Error -> {
                onError(result.message, result.ex)
            }
        }
    }



}