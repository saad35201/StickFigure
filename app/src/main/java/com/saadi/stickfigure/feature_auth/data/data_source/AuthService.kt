package com.saadi.stickfigure.feature_auth.data.data_source

import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInRequest
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInResponse
import com.saadi.stickfigure.feature_auth.domain.model.sign_up.SignUpRequest
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthService {

    @POST("/api/v1/login")
    suspend fun signIn(
        @Body signInRequest: SignInRequest
    ): Response<SignInResponse>

    @FormUrlEncoded
    @POST("/api/v1/register")
    suspend fun signUp(
        @Field("profile_pic") profilePic: MultipartBody.Part,
        @Field("username") username: String
    ): Response<SignInResponse>

}