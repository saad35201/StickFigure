package com.saadi.stickfigure.feature_auth.domain.usecase

import com.saadi.stickfigure.feature_auth.domain.model.login.LoginRequest
import com.saadi.stickfigure.feature_auth.domain.model.login.LoginResponse
import com.saadi.stickfigure.feature_auth.domain.repository.AuthRepository
import com.saadi.stickfigure.utils.NetworkResult
import retrofit2.Response

class Login(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(loginRequest: LoginRequest): NetworkResult<LoginResponse>{
        if (loginRequest.email.isNullOrEmpty()){
            return NetworkResult.Error("Enter valid email or username")
        }
        if (loginRequest.password.isNullOrEmpty()){
            return NetworkResult.Error("Enter valid password")
        }
        return repository.login(loginRequest = loginRequest)
    }

}