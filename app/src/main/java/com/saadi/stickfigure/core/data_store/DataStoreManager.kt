package com.saadi.stickfigure.core.data_store

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStoreFile
import com.saadi.stickfigure.utils.Constants
import com.saadi.stickfigure.utils.Constants.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager constructor(context: Context) {

    private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create {
        context.applicationContext.preferencesDataStoreFile(Constants.DataStore)
    }

    suspend fun save(key: String, value: Any?) {
        dataStore.edit { preferences ->
            when (value) {
                is String -> {
                    preferences[stringPreferencesKey(key)] = value
                    Log.e(TAG, "String saved: $value")
                }
                is Int -> {
                    preferences[intPreferencesKey(key)] = value
                    Log.e(TAG, "Integer saved: $value")
                }
                is Long -> {
                    preferences[longPreferencesKey(key)] = value
                    Log.e(TAG, "Long saved: $value")
                }
                is Float -> {
                    preferences[floatPreferencesKey(key)] = value
                    Log.e(TAG, "Float saved: $value")
                }
                is Boolean -> {
                    preferences[booleanPreferencesKey(key)] = value
                    Log.e(TAG, "Boolean saved: $value")
                }
                else -> throw IllegalArgumentException("Unsupported preference type")
            }
        }
    }

    fun getString(key: String, defaultValue: String?): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)] ?: defaultValue
        }
    }

    fun getInt(key: String, defaultValue: Int): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[intPreferencesKey(key)] ?: defaultValue
        }
    }

    fun getLong(key: String, defaultValue: Long): Flow<Long> {
        return dataStore.data.map { preferences ->
            preferences[longPreferencesKey(key)] ?: defaultValue
        }
    }

    fun getFloat(key: String, defaultValue: Float): Flow<Float> {
        return dataStore.data.map { preferences ->
            preferences[floatPreferencesKey(key)] ?: defaultValue
        }
    }

    fun getBoolean(key: String): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(key)] ?: false
        }
    }

    suspend fun remove(key: String) {
        dataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(key))
            preferences.remove(intPreferencesKey(key))
            preferences.remove(longPreferencesKey(key))
            preferences.remove(floatPreferencesKey(key))
            preferences.remove(booleanPreferencesKey(key))
        }
    }

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}