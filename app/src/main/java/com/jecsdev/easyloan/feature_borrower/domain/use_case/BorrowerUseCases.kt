package com.jecsdev.easyloan.feature_borrower.domain.use_case

/**
 * Borrower use cases class.
 * @param getBorrowers: Get borrowers use case.
 * @param getBorrower: Get borrower use case.
 * @param addBorrower: Add borrower use case.
 * @param deleteBorrower: Delete borrower use case.
 * @param updateBorrower: Update borrower use case.
 */
data class BorrowerUseCases(
    val getBorrowers: GetBorrowers,
    val getBorrower: GetBorrower,
    val addBorrower: AddBorrower,
    val deleteBorrower: DeleteBorrower,
    val updateBorrower: UpdateBorrower
)
