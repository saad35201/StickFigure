package com.saadi.stickfigure.feature_auth.presentation.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInRequest
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInResponse
import com.saadi.stickfigure.feature_auth.domain.model.sign_up.SignUpRequest
import com.saadi.stickfigure.feature_auth.domain.model.sign_up.SignUpResponse
import com.saadi.stickfigure.feature_auth.domain.usecase.AuthUseCases
import com.saadi.stickfigure.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VmSignUp @Inject constructor(
    private val useCases: AuthUseCases
): ViewModel() {

    private val mSignUpLiveData : MutableLiveData<NetworkResult<SignUpResponse>> by lazy { MutableLiveData() }
    val signUpLiveData: LiveData<NetworkResult<SignUpResponse>> get() = mSignUpLiveData

    fun signUp(signUpRequest: SignUpRequest){
        viewModelScope.launch {
            mSignUpLiveData.postValue(NetworkResult.Loading())
            mSignUpLiveData.postValue(useCases.signUp(request = signUpRequest))
        }
    }

}