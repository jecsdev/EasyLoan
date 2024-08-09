package com.jecsdev.easyloan.feature_borrower.domain.use_case

import com.jecsdev.easyloan.feature_borrower.data.model.Borrower
import com.jecsdev.easyloan.feature_borrower.domain.repository.BorrowerRepository

/**
 * Update a borrower in the repository.
 * @param borrowerRepository: The repository used to update the borrower.
 */
class UpdateBorrower(private val borrowerRepository: BorrowerRepository) {
    suspend operator fun invoke(borrower: Borrower) {
        borrowerRepository.updateBorrower(borrower)
    }
}