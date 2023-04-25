package com.saadi.stickfigure.feature_home_drawer.presentation

import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.saadi.stickfigure.core.data_store.UserDataStore
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VmHomeBase @Inject constructor(
    private val userDataStore: UserDataStore
): ViewModel() {

    private val mUser: MutableLiveData<User> by lazy { MutableLiveData() }
    val userLiveData: LiveData<User> get() =  mUser

    fun getUser(){
        viewModelScope.launch {
            userDataStore.getUser().collect { userJson ->
                if (!userJson.isNullOrEmpty()) { // Check if the userJson is not null or empty
                    val user = Gson().fromJson(userJson, User::class.java)
                    mUser.postValue(user)
                }
            }
        }
    }

}