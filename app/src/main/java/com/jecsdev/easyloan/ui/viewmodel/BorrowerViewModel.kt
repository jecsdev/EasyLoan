package com.jecsdev.easyloan.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jecsdev.easyloan.feature_authentication.repository.AuthRepository
import com.jecsdev.easyloan.feature_borrower.data.model.Borrower
import com.jecsdev.easyloan.feature_borrower.domain.use_case.BorrowerUseCases
import com.jecsdev.easyloan.ui.state.BorrowerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * BorrowerViewModel class.
 * @param borrowerUseCases: BorrowerUseCases instance.
 */
@HiltViewModel
class BorrowerViewModel @Inject constructor(
    private val borrowerUseCases: BorrowerUseCases,
    authRepository: AuthRepository
) :
    ViewModel() {

    private val _state = MutableStateFlow<BorrowerState>(BorrowerState.Loading)
    val state: StateFlow<BorrowerState> = _state.asStateFlow()

    private val _borrowerState = MutableStateFlow<Borrower?>(null)
    val borrowerState: StateFlow<Borrower?> = _borrowerState

    private var getBorrowersJob: Job? = null
    private val userId = authRepository.getSignedInUser()?.userId

    init {
        getBorrowers(userId = userId)
    }

    /**
     * Retrieves a list of borrowers.
     */
    private fun getBorrowers(userId: String?) {
        getBorrowersJob?.cancel()
        getBorrowersJob = viewModelScope.launch {
            try {
                borrowerUseCases.getBorrowers(userId).flowOn(Dispatchers.IO).collect { borrowers ->
                    _state.value = BorrowerState.Success(borrowers = borrowers)
                }
            } catch (exception: Exception) {
                _state.value = BorrowerState.Error(exception.message.toString())
            }
        }
    }

    /*
     * Retrieves a borrower from the repository.
     */
    suspend fun getBorrower(id: String){
        getBorrowersJob?.cancel()
        getBorrowersJob = viewModelScope.launch {
            try {
                borrowerUseCases.getBorrower(id).flowOn(Dispatchers.IO).collect { borrower ->
                    _borrowerState.value = borrower
                }
            } catch (exception: Exception) {
                _state.value = BorrowerState.Error(exception.message.toString())
            }
        }
    }

    /**
     * Adds a borrower to the repository.
     */
    suspend fun addBorrower(borrower: Borrower) {
        viewModelScope.launch {
            borrowerUseCases.addBorrower(borrower)
            getBorrowers(userId)
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

    fun getSignedUserId(): String? = userId
}