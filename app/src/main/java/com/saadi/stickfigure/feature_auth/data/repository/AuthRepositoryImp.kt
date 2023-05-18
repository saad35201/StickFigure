package com.saadi.stickfigure.feature_auth.data.repository

import com.saadi.stickfigure.core.base.BaseRepository
import com.saadi.stickfigure.feature_auth.data.data_source.AuthService
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInRequest
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInResponse
import com.saadi.stickfigure.feature_auth.domain.model.sign_up.SignUpRequest
import com.saadi.stickfigure.feature_auth.domain.model.sign_up.SignUpResponse
import com.saadi.stickfigure.feature_auth.domain.repository.AuthRepository
import com.saadi.stickfigure.utils.NetworkResult
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Multipart

class AuthRepositoryImp(

    private var authService: AuthService
) : AuthRepository, BaseRepository() {

    override suspend fun signIn(signInRequest: SignInRequest): NetworkResult<SignInResponse> {
        return safeApiCall { authService.signIn(signInRequest) }
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): NetworkResult<SignUpResponse> {
        return safeApiCall {
            authService.signUp(
                profilePic = signUpRequest.profilePic!!,
                username = signUpRequest.username!!.toRequestBody(MultipartBody.FORM),
                firstName = signUpRequest.firstName!!.toRequestBody(MultipartBody.FORM),
                lastName = signUpRequest.lastName!!.toRequestBody(MultipartBody.FORM),
                name = "${signUpRequest.firstName} ${signUpRequest.lastName}".toRequestBody(MultipartBody.FORM),
                email = signUpRequest.email!!.toRequestBody(MultipartBody.FORM),
                phoneNo = signUpRequest.phoneNumber!!.toRequestBody(MultipartBody.FORM),
                password = signUpRequest.password!!.toRequestBody(MultipartBody.FORM),
                passwordConfirmation = signUpRequest.passwordConfirmation!!.toRequestBody(MultipartBody.FORM)
                )
        }
    }

    override suspend fun forgotPassword(email: String): NetworkResult<SignUpResponse> {
        return safeApiCall { authService.forgotPassword(email = email) }
    }

}