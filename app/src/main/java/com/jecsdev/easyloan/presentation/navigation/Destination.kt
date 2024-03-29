package com.jecsdev.easyloan.presentation.navigation

/**
 * Class that handles the destinations from whole app.
 * @param route destination to navigate.
 */
sealed class Destination(val route: String) {
    object LogIn : Destination("login")
    object Dashboard:  Destination("dashboard")

}