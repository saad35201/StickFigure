package com.saadi.stickfigure.feature_auth.domain.model.sign_in

import com.fasterxml.jackson.annotation.JsonProperty

data class SignInRequest(

	@field:JsonProperty("password")
	val password: String? = null,

	@field:JsonProperty("email")
	val email: String? = null
)
