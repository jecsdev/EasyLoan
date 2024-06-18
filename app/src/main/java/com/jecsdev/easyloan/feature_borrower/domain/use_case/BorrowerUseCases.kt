package com.jecsdev.easyloan.feature_borrower.domain.use_case

data class BorrowerUseCases(
    val getBorrowers: GetBorrowers,
    val getBorrower: GetBorrower,
    val addBorrower: AddBorrower,
    val deleteBorrower: DeleteBorrower,
    val updateBorrower: UpdateBorrower
)
