package com.jecsdev.easyloan.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jecsdev.easyloan.feature_borrower.data.model.Borrower
import com.jecsdev.easyloan.feature_borrower.domain.use_case.BorrowerUseCases
import com.jecsdev.easyloan.ui.state.BorrowerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * BorrowerViewModel class.
 * @param borrowerUseCases: BorrowerUseCases instance.
 */
@HiltViewModel
class BorrowerViewModel @Inject constructor(private val borrowerUseCases: BorrowerUseCases) :
    ViewModel() {

    private val _state = mutableStateOf<BorrowerState>(BorrowerState.Loading)
    val state: State<BorrowerState> = _state

    private var getBorrowersJob: Job? = null

    init {
        getBorrowers()
    }

    /**
     * Retrieves a list of borrowers.
     */
    private fun getBorrowers() {
        getBorrowersJob?.cancel()
        getBorrowersJob = viewModelScope.launch {
            borrowerUseCases.getBorrowers().onEach { borrowers ->
                _state.value = BorrowerState.Success(borrowers)
            }.launchIn(viewModelScope)
        }
    }
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
            getBorrowers()
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