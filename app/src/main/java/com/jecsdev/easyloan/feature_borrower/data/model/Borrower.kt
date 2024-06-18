package com.jecsdev.easyloan.feature_borrower.data.model


/**
 * Borrower data class.
 */
data class Borrower(
    var id: String,
    var name: String,
    var lastName: String,
    var identificationNumber: String,
    var address: String,
    var photo: String
)