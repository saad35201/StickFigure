package com.saadi.stickfigure.feature_auth.presentation.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInRequest
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInResponse
import com.saadi.stickfigure.feature_auth.domain.usecase.AuthUseCases
import com.saadi.stickfigure.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VmSignIn @Inject constructor(
    private val useCases: AuthUseCases
): ViewModel() {

    private val mSignInLiveData : MutableLiveData<NetworkResult<SignInResponse>> by lazy { MutableLiveData() }
    val signInLiveData: LiveData<NetworkResult<SignInResponse>> get() = mSignInLiveData

    fun signIn(signInRequest: SignInRequest){
        viewModelScope.launch {
            mSignInLiveData.postValue(NetworkResult.Loading())
            mSignInLiveData.postValue(useCases.signIn(signInRequest = signInRequest))
        }
    }

    //clearing live data for unique scenario going back
    //and forth between fragments
    fun clear(){
        mSignInLiveData.postValue(null)
    }

}