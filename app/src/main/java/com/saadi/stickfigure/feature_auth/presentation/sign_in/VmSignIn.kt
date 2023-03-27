package com.saadi.stickfigure.feature_auth.presentation.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saadi.stickfigure.feature_auth.domain.model.login.LoginRequest
import com.saadi.stickfigure.feature_auth.domain.model.login.LoginResponse
import com.saadi.stickfigure.feature_auth.domain.usecase.AuthUseCases
import com.saadi.stickfigure.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VmSignIn @Inject constructor(
    private val useCases: AuthUseCases
): ViewModel() {

    private val mSignInLiveData : MutableLiveData<NetworkResult<LoginResponse>> by lazy { MutableLiveData() }
    val signInLiveData: LiveData<NetworkResult<LoginResponse>> get() = mSignInLiveData

    fun signIn(loginRequest: LoginRequest){
        viewModelScope.launch {
            mSignInLiveData.postValue(NetworkResult.Loading())
            mSignInLiveData.postValue(useCases.login(loginRequest = loginRequest))
        }
    }

}