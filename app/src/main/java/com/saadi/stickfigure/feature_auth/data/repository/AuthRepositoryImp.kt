package com.saadi.stickfigure.feature_auth.data.repository

import com.saadi.stickfigure.core.BaseRepository
import com.saadi.stickfigure.feature_auth.data.data_source.AuthService
import com.saadi.stickfigure.feature_auth.domain.model.login.LoginRequest
import com.saadi.stickfigure.feature_auth.domain.model.login.LoginResponse
import com.saadi.stickfigure.feature_auth.domain.repository.AuthRepository
import com.saadi.stickfigure.utils.NetworkResult
import retrofit2.Response

class AuthRepositoryImp(
    private var authService: AuthService
) : AuthRepository, BaseRepository() {

    override suspend fun login(loginRequest: LoginRequest): NetworkResult<LoginResponse> {
        return safeApiCall { authService.login(loginRequest) }
    }

}