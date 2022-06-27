package com.dacodes.weatherapp.core.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<VDB : ViewDataBinding>(
    @LayoutRes
    protected val layoutId: Int,
) : Fragment(){

    protected val loader by lazy { LoaderDialog(requireActivity()) }
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

    internal fun hideKeyboard() {
        val view = requireActivity().findViewById<View>(android.R.id.content)
        if (view != null) {
            val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    protected fun showErrorMessage(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }


}