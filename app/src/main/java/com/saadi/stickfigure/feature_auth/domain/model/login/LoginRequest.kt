package com.saadi.stickfigure.feature_auth.domain.model.login

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequest(

	@field:JsonProperty("password")
	val password: String? = null,

	@field:JsonProperty("email")
	val email: String? = null
)
