package com.lampa.skeleton.repository

import com.lampa.skeleton.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class TestRepository @Inject constructor() {
    suspend fun getTestResult(): DataState<String> {
        return try {
            DataState.Success("Test success")
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }

    suspend fun getTestFlow(): Flow<DataState<String>> = flow {
        try {
            emit(DataState.Success("Flow success"))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}