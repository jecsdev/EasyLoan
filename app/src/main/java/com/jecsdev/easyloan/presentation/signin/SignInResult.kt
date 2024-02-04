package com.jecsdev.easyloan.presentation.signin

import com.jecsdev.easyloan.data.entity.user.UserData

/**
 * This class the Sign In result when user authenticates
 */
data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)


