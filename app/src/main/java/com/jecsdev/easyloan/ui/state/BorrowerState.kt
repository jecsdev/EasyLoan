package com.jecsdev.easyloan.ui.state

import com.jecsdev.easyloan.feature_borrower.data.model.Borrower

sealed class BorrowerState {
    object Empty : BorrowerState()
    object Loading : BorrowerState()
    data class Success(val borrowers: List<Borrower> = emptyList()): BorrowerState()
    data class Editing(
        val name: String,
        val lastName: String,
        val identificationNumber: String,
        val address: String
    ) : BorrowerState()
    data class Error(val message: String): BorrowerState()
    data class ReadyToSave(
        val borrower: Borrower
    ) : BorrowerState()
}
