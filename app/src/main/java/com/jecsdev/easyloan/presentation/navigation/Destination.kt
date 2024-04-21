package com.jecsdev.easyloan.presentation.navigation

/**
 * Class that handles the destinations from whole app.
 * @param route destination to navigate.
 */
sealed class Destination(val route: String) {
    object LogIn: Destination("login")
    object Home: Destination("home")
    object DebtorsList: Destination("debtors_list")
    object CreateDebtor: Destination("create_debtor")
    object CreateLoan: Destination("create_loan")
}