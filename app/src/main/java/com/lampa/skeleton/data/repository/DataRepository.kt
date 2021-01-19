package com.lampa.skeleton.data.repository

import androidx.datastore.preferences.core.stringPreferencesKey
import com.lampa.skeleton.data.repository.DataRepository.PreferencesKey.USER_ID
import com.lampa.skeleton.util.DataStoreManager
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val dataStoreManager: DataStoreManager
) {
    private object PreferencesKey {
        val USER_ID = stringPreferencesKey("user_id")
    }
    suspend fun saveUserId(userId: String) {
        dataStoreManager.saveStringValue(USER_ID, userId)
    }
    suspend fun readUserId(): String {
        return dataStoreManager.getStringValue(USER_ID)
    }
}