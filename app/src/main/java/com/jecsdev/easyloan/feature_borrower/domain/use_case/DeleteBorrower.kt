package com.jecsdev.easyloan.feature_borrower.domain.use_case

import com.jecsdev.easyloan.feature_borrower.data.model.Borrower
import com.jecsdev.easyloan.feature_borrower.domain.repository.BorrowerRepository

/**
 * Delete a borrower from the repository.
 * @param borrowerRepository: The repository used to delete the borrower.
 */
class DeleteBorrower(private val borrowerRepository: BorrowerRepository) {
    suspend operator fun invoke(borrower: Borrower) {
        borrowerRepository.deleteBorrower(borrower)
    }

}