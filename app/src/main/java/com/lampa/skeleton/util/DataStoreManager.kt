package com.lampa.skeleton.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager  @Inject constructor(
    private val dataStore: DataStore<Preferences>
){
    suspend fun saveStringValue(key: Preferences.Key<String>, userId: String) {
        dataStore.edit { preferences ->
            preferences[key] = userId
        }
    }
    suspend fun getStringValue(key: Preferences.Key<String>): String {
        val preferences = dataStore.data.first()
        return preferences[key] ?: "-1"
    }
}