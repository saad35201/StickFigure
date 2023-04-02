package com.saadi.stickfigure.feature_auth.domain.usecase

import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInRequest
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInResponse
import com.saadi.stickfigure.feature_auth.domain.repository.AuthRepository
import com.saadi.stickfigure.utils.NetworkResult

class SignIn(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(signInRequest: SignInRequest): NetworkResult<SignInResponse> {
        if (signInRequest.email.isNullOrEmpty()) {
            return NetworkResult.Error("Enter valid email or username")
        }
        if (signInRequest.password.isNullOrEmpty()) {
            return NetworkResult.Error("Enter valid password")
        }
        return repository.signIn(signInRequest = signInRequest)
    }

}