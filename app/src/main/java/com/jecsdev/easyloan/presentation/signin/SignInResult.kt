package com.jecsdev.easyloan.presentation.signin

/**
 * This class the Sign In result when user authenticates
 */
data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

//TODO: Refactor this crap
data class UserData(
    val userId: String,
    val userName: String,
    val profilePictureUrl: String?
)
