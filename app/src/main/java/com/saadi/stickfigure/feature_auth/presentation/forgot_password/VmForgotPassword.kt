package com.saadi.stickfigure.feature_auth.presentation.forgot_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInResponse
import com.saadi.stickfigure.feature_auth.domain.model.sign_up.SignUpResponse
import com.saadi.stickfigure.feature_auth.domain.usecase.AuthUseCases
import com.saadi.stickfigure.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VmForgotPassword @Inject constructor(
    private val useCases: AuthUseCases
): ViewModel() {

    private val mForgotPasswordLiveData : MutableLiveData<NetworkResult<SignUpResponse>> by lazy { MutableLiveData() }
    val forgotPasswordLiveData: LiveData<NetworkResult<SignUpResponse>> get() = mForgotPasswordLiveData

    fun forgotPassword(email: String){
        viewModelScope.launch {
            mForgotPasswordLiveData.postValue(NetworkResult.Loading())
            mForgotPasswordLiveData.postValue(useCases.forgotPassword(email = email))
        }
    }

}