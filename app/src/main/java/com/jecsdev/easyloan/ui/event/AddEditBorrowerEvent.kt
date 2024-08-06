package com.jecsdev.easyloan.ui.event

/**
 * This is the borrower creation event.
 */
sealed class AddEditBorrowerEvent {
    data class EnteredName(val value: String) : AddEditBorrowerEvent()
    data class EnteredLastName(val value: String) : AddEditBorrowerEvent()
    data class EnteredIdentificationNumber(val value: String) : AddEditBorrowerEvent()
    data class EnteredAddress(val value: String) : AddEditBorrowerEvent()
    data class ChangePhoto(val value: String) : AddEditBorrowerEvent()
}