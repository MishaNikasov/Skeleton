package com.lampa.skeleton.view.base

import androidx.fragment.app.Fragment
import com.lampa.skeleton.extensions.showToast
import java.lang.Exception

open class BaseFragment : Fragment() {
    fun displayError(exception: Exception) {
        requireContext().showToast(exception.localizedMessage ?: "Error")
    }
}