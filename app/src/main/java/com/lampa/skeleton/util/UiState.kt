package com.lampa.skeleton.util

sealed class UiState {
    data class Error(val exception: Exception): UiState()
    data class Loading(val inProgress: Boolean): UiState()
    object Empty: UiState()
}