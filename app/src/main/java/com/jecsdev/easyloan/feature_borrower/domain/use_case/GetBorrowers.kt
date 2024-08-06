package com.jecsdev.easyloan.feature_borrower.domain.use_case

import com.jecsdev.easyloan.feature_borrower.data.model.Borrower
import com.jecsdev.easyloan.feature_borrower.domain.repository.BorrowerRepository
import kotlinx.coroutines.flow.Flow

/**
 * Get borrowers use case.
 * @param borrowerRepository: BorrowerRepositoryImplementation instance.
 * */
class GetBorrowers (private val borrowerRepository: BorrowerRepository){
    suspend operator fun invoke(userId: String?): Flow<List<Borrower>> = borrowerRepository.getBorrowers(userId)
}