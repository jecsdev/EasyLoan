package com.jecsdev.easyloan.ui.state

/**
 * Manages the Dashboard's state.
 *   @param isSuccessful returns if was a successful transaction.
 *   @param isLoading returns if the Sign in is loading.
 *   @param isError returns if there is an error.
 */
data class DashboardState(
    val isSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val isError: String? = null
)
