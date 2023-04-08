package com.saadi.stickfigure.feature_auth.presentation.splash

import androidx.lifecycle.*
import com.saadi.stickfigure.core.data_store.UserDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VmSplash @Inject constructor(
    private val userDataStore: UserDataStore
): ViewModel() {

    private val mSessionLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val sessionLiveData: LiveData<Boolean> get() =  mSessionLiveData


    fun getSession(){
        viewModelScope.launch {
            userDataStore.getIsRememberMe().asLiveData().observeForever { isRememberMe ->
                if (isRememberMe == true){
                    viewModelScope.launch {
                        userDataStore.getIsLoggedIn().asLiveData().observeForever { isLoggedIn ->
                            if (isLoggedIn == true) {
                                mSessionLiveData.postValue(true)
                            }
                        }
                    }
                } else {
                    mSessionLiveData.postValue(false)
                }
            }
        }
    }

}