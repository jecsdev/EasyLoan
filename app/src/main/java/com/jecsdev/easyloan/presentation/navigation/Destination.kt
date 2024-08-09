package com.jecsdev.easyloan.presentation.navigation

/**
 * Class that handles the destinations from whole app.
 * @param route destination to navigate.
 */
sealed class Destination(val route: String) {
    object LogIn: Destination("login")
    object Home: Destination("home")
    object BorrowersList: Destination("borrowers_list")
    object CreateBorrower: Destination("create_borrower")
    object CreateLoan: Destination("create_loan")
    object BorrowerDetails: Destination("borrower_details")
}