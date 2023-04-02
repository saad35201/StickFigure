package com.saadi.stickfigure.di

import android.content.Context
import com.saadi.stickfigure.core.data_store.DataStoreManager
import com.saadi.stickfigure.core.data_store.UserDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Singleton
    @Provides
    fun providesDataStoreManager(context: Context): DataStoreManager {
        return DataStoreManager(context = context)
    }

    @Singleton
    @Provides
    fun providesUserDataStore(manager: DataStoreManager): UserDataStore {
        return UserDataStore(manager = manager)
    }

}