package com.saadi.stickfigure.feature_auth.domain.model.sign_up

import com.fasterxml.jackson.annotation.JsonProperty
import okhttp3.MultipartBody

data class SignUpRequest(

    @field:JsonProperty("profile_pic")
    val profilePic: MultipartBody.Part? = null,

    @field:JsonProperty("username")
    val username: String? = null,

    @field:JsonProperty("first_name")
    val firstName: String? = null,

    @field:JsonProperty("last_name")
    val lastName: String? = null,

    @field:JsonProperty("email")
    val email: String? = null,

    @field:JsonProperty("phone_no")
    val phoneNumber: String? = null,

    @field:JsonProperty("password")
    val password: String? = null,

    @field:JsonProperty("password_confirmation")
    val passwordConfirmation: String? = null,
)