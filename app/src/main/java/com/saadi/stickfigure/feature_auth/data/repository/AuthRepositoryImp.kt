package com.saadi.stickfigure.feature_auth.data.repository

import com.saadi.stickfigure.core.base.BaseRepository
import com.saadi.stickfigure.feature_auth.data.data_source.AuthService
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInRequest
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInResponse
import com.saadi.stickfigure.feature_auth.domain.model.sign_up.SignUpRequest
import com.saadi.stickfigure.feature_auth.domain.repository.AuthRepository
import com.saadi.stickfigure.utils.NetworkResult

class AuthRepositoryImp(

    private var authService: AuthService
) : AuthRepository, BaseRepository() {

    override suspend fun signIn(signInRequest: SignInRequest): NetworkResult<SignInResponse> {
        return safeApiCall { authService.signIn(signInRequest) }
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): NetworkResult<SignInResponse> {
        return safeApiCall {
            authService.signUp(
                signUpRequest.profilePic!!,
                signUpRequest.username!!
            )
        }
    }

}