package com.jecsdev.easyloan.utils.constants

const val firebaseClientId = "184255035025-j4cj0lb3v0sdr92om9valv7kqdv8edd7.apps.googleusercontent.com"

object FirebaseCollectionNames {
    var borrowers = "borrowers"
    var loan = "loan"
    var user = "user"
    var id = "id"
}

object ExceptionConstants {
    const val EXCEPTION_TAG = "Exception"
    const val EXCEPTION_MESSAGE = "Exception message"
    const val SIGN_IN_EXCEPTION_TAG = "GetCredentialException"
    const val GOOGLE_ID_TOKEN_PARSING_EXCEPTION_TAG = "GoogleIdTokenParsingException"
    const val LOGIN_TAG = "Login"
}