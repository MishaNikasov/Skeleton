package com.lampa.skeleton.view.base

import androidx.lifecycle.ViewModel
import com.lampa.skeleton.util.UiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

open class BaseViewModel: ViewModel() {
    protected val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState : StateFlow<UiState> = _uiState

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext: CoroutineContext, throwable: Throwable ->

    }
}