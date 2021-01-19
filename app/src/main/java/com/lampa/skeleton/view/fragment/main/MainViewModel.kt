package com.lampa.skeleton.view.fragment.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor() : ViewModel() {

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
}