package com.jecsdev.easyloan.feature_borrower.domain.use_case

import com.jecsdev.easyloan.feature_borrower.data.model.Borrower
import com.jecsdev.easyloan.feature_borrower.data.repository.BorrowerRepositoryImplementation
import kotlinx.coroutines.flow.Flow

/**
 * Get borrowers use case.
 * @param borrowerRepository: BorrowerRepositoryImplementation instance.
 * */
class GetBorrowers (private val borrowerRepository: BorrowerRepositoryImplementation){
    suspend operator fun invoke(): Flow<List<Borrower>> = borrowerRepository.getBorrowers()
}