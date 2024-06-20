package com.jecsdev.easyloan.feature_borrower.domain.use_case

import com.jecsdev.easyloan.feature_borrower.data.model.Borrower
import com.jecsdev.easyloan.feature_borrower.data.repository.BorrowerRepositoryImplementation
import kotlinx.coroutines.flow.Flow

/**
 * Retrieves a borrower from the repository.
 */
class GetBorrower(private val borrowerRepository: BorrowerRepositoryImplementation) {
    suspend operator fun invoke(id: String): Flow<Borrower?> = borrowerRepository.getBorrower(id)
}