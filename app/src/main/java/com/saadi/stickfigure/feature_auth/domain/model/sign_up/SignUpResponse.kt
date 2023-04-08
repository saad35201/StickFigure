package com.saadi.stickfigure.feature_auth.domain.model.sign_up

import com.fasterxml.jackson.annotation.JsonProperty
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.User

data class SignUpResponse(

    @field:JsonProperty("message")
    val message: String? = null,

)