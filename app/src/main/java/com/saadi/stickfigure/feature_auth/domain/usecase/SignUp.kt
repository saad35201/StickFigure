package com.saadi.stickfigure.feature_auth.domain.usecase

import android.content.Context
import com.hbb20.CountryCodePicker
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInResponse
import com.saadi.stickfigure.feature_auth.domain.model.sign_up.SignUpRequest
import com.saadi.stickfigure.feature_auth.domain.repository.AuthRepository
import com.saadi.stickfigure.utils.NetworkResult
import com.saadi.stickfigure.utils.isValidEmailAddress
import com.saadi.stickfigure.utils.isValidName
import com.saadi.stickfigure.utils.isValidNumber
import javax.inject.Inject

class SignUp(
    private val repository: AuthRepository
) {


    suspend operator fun invoke(request: SignUpRequest): NetworkResult<SignInResponse> {
        if (request.profilePic == null) {
            return NetworkResult.Error("Please select profile picture")
        }
        if (request.username.isNullOrEmpty()) {
            return NetworkResult.Error("Enter username")
        }
        if (!request.firstName!!.isValidName()) {
            return NetworkResult.Error("Enter valid first name")
        }
        if (!request.lastName!!.isValidName()) {
            return NetworkResult.Error("Enter valid last name")
        }
        if (!request.email!!.isValidEmailAddress()) {
            return NetworkResult.Error("Enter valid email or username")
        }
        if (!request.phoneNumber!!.isValidNumber()) {
            return NetworkResult.Error("Enter valid phone number")
        }
        if (request.password.isNullOrEmpty()) {
            return NetworkResult.Error("Enter valid password")
        }
        if (request.password != request.passwordConfirmation) {
            return NetworkResult.Error("Confirm password doesn't match with password")
        }
        return repository.signUp(signUpRequest = request)
    }

}