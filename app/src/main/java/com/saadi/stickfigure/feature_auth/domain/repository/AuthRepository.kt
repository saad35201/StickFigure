package com.saadi.stickfigure.feature_auth.domain.repository

import com.saadi.stickfigure.feature_auth.domain.model.login.LoginRequest
import com.saadi.stickfigure.feature_auth.domain.model.login.LoginResponse
import com.saadi.stickfigure.utils.NetworkResult
import retrofit2.Response

interface AuthRepository {

    suspend fun login(loginRequest: LoginRequest): NetworkResult<LoginResponse>

}