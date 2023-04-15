package com.saadi.stickfigure.feature_auth.domain.usecase

import android.content.Context
import com.hbb20.CountryCodePicker
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInResponse
import com.saadi.stickfigure.feature_auth.domain.model.sign_up.SignUpRequest
import com.saadi.stickfigure.feature_auth.domain.model.sign_up.SignUpResponse
import com.saadi.stickfigure.feature_auth.domain.repository.AuthRepository
import com.saadi.stickfigure.utils.NetworkResult
import com.saadi.stickfigure.utils.isValidEmailAddress
import com.saadi.stickfigure.utils.isValidName
import com.saadi.stickfigure.utils.isValidNumber
import javax.inject.Inject

class ForgotPassword(
    private val repository: AuthRepository
) {


    suspend operator fun invoke(email: String): NetworkResult<SignUpResponse> {
        if (!email.isValidEmailAddress()) {
            return NetworkResult.Error("Enter valid email address")
        }
        return repository.forgotPassword(email = email)
    }

}