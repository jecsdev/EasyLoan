package com.jecsdev.easyloan.feature_borrower.domain.repository

import com.jecsdev.easyloan.feature_borrower.data.model.Borrower
import kotlinx.coroutines.flow.Flow

/**
 * Borrower Repository interface.
 */
interface BorrowerRepository {
    suspend fun getBorrowers(userId: String?): Flow<List<Borrower>>
    suspend fun getBorrower(id: String): Flow<Borrower?>
    suspend fun addBorrower(borrower: Borrower)
    suspend fun updateBorrower(borrower: Borrower)
    suspend fun deleteBorrower(borrower: Borrower)
}