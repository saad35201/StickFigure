package com.saadi.stickfigure.feature_auth.data.data_source

import com.saadi.stickfigure.feature_auth.domain.model.login.LoginRequest
import com.saadi.stickfigure.feature_auth.domain.model.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/api/v1/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

}