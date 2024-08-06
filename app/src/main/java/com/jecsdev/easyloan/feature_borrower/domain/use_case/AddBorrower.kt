package com.jecsdev.easyloan.feature_borrower.domain.use_case

import com.jecsdev.easyloan.feature_borrower.data.model.Borrower
import com.jecsdev.easyloan.feature_borrower.domain.repository.BorrowerRepository

/**
 * Retrieves a list of borrowers.
 * @param borrowerRepository: The repository used to retrieve the borrowers.
 */
class AddBorrower(private val borrowerRepository: BorrowerRepository) {
    suspend operator fun invoke(borrower: Borrower) {
        borrowerRepository.addBorrower(borrower)
    }
}