package com.jecsdev.easyloan.feature_borrower.data.model


/**
 * Borrower data class.
 * @param id: The ID of the borrower.
 * @param name: The name of the borrower.
 * @param identificationNumber: The identification number of the borrower.
 * @param address: The address of the borrower.
 * @param photo: The photo of the borrower.
 */
data class Borrower(
    var id: String? = "",
    var name: String,
    var identificationNumber: String,
    var address: String,
    var photo: String
) {
    // Empty constructor.
    constructor() : this("", "", "", "", "")
}