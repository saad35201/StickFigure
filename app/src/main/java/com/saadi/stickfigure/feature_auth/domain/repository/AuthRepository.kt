package com.saadi.stickfigure.feature_auth.domain.repository

import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInRequest
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInResponse
import com.saadi.stickfigure.feature_auth.domain.model.sign_up.SignUpRequest
import com.saadi.stickfigure.feature_auth.domain.model.sign_up.SignUpResponse
import com.saadi.stickfigure.utils.NetworkResult

interface AuthRepository {

    suspend fun signIn(signInRequest: SignInRequest): NetworkResult<SignInResponse>

    suspend fun signUp(signUpRequest: SignUpRequest): NetworkResult<SignUpResponse>

}