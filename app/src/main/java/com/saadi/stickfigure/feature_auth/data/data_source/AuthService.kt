package com.saadi.stickfigure.feature_auth.data.data_source

import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInRequest
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInResponse
import com.saadi.stickfigure.feature_auth.domain.model.sign_up.SignUpRequest
import com.saadi.stickfigure.feature_auth.domain.model.sign_up.SignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @Multipart
    @POST("/api/v1/register")
    suspend fun signUp(
        @Part("profile_pic") profilePic: MultipartBody.Part,
        @Part("user_name") username: RequestBody,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phoneNo: RequestBody,
        @Part("password") password: RequestBody,
        @Part("password_confirmation") passwordConfirmation: RequestBody,
    ): Response<SignUpResponse>

}