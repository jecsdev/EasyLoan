package com.jecsdev.easyloan.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jecsdev.easyloan.feature_borrower.data.model.Borrower
import com.jecsdev.easyloan.feature_borrower.domain.use_case.BorrowerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * BorrowerViewModel class.
 * @param borrowerUseCases: BorrowerUseCases instance.
 */
@HiltViewModel
class BorrowerViewModel @Inject constructor(private val borrowerUseCases: BorrowerUseCases) :
    ViewModel() {
    /**
     * Retrieves a list of borrowers.
     */
    suspend fun getBorrowers() = borrowerUseCases.getBorrowers()

    /*
     * Retrieves a borrower from the repository.
     */
    suspend fun getBorrower(id: String) = borrowerUseCases.getBorrower(id)

    /**
     * Adds a borrower to the repository.
     */
    suspend fun addBorrower(borrower: Borrower) {
        viewModelScope.launch {
            borrowerUseCases.addBorrower(borrower)
        }
    }

    /**
     * Deletes a borrower from the repository.
     */
    suspend fun deleteBorrower(borrower: Borrower) {
        viewModelScope.launch {
            borrowerUseCases.deleteBorrower(borrower)
        }
    }

    /**
     * Updates a borrower in the repository.
     */
    suspend fun updateBorrower(borrower: Borrower) {
        viewModelScope.launch {
            borrowerUseCases.updateBorrower(borrower)
        }
    }
}