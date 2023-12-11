package com.jecsdev.easyloan.ui.state

data class SignInState(
    val isSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val isError: String? = null
)
