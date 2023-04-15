package com.saadi.stickfigure.feature_auth.domain.usecase

data class AuthUseCases(
    val signIn: SignIn,
    val signUp: SignUp,
    val forgotPassword: ForgotPassword
)