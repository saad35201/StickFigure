package com.saadi.stickfigure.core.data_store

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStoreFile
import com.saadi.stickfigure.utils.Constants
import com.saadi.stickfigure.utils.Constants.TAG
import kotlinx.coroutines.flow.*

class DataStoreManager constructor(context: Context) {

    private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create {
        context.applicationContext.preferencesDataStoreFile(Constants.DataStore)
    }

    suspend fun saveData(key: String, value: Any?) {
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

    suspend fun <T> getData(key: String, defaultValue: T): Flow<T> = flow {
        val preferencesKey = when (defaultValue) {
            is String -> stringPreferencesKey(key)
            is Int -> intPreferencesKey(key)
            is Long -> longPreferencesKey(key)
            is Float -> floatPreferencesKey(key)
            is Boolean -> booleanPreferencesKey(key)
            else -> throw IllegalArgumentException("Unsupported preference type")
        }
        val preferences = dataStore.data.first()
        val value = preferences[preferencesKey] ?: defaultValue
        emit(value as T)
    }.catch { exception ->
        if (exception is IOException) {
            emit(defaultValue)
        } else {
            throw exception
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