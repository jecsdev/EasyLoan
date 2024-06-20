package com.jecsdev.easyloan.di

import com.jecsdev.easyloan.feature_borrower.data.repository.BorrowerRepositoryImplementation
import com.jecsdev.easyloan.feature_borrower.domain.use_case.AddBorrower
import com.jecsdev.easyloan.feature_borrower.domain.use_case.BorrowerUseCases
import com.jecsdev.easyloan.feature_borrower.domain.use_case.DeleteBorrower
import com.jecsdev.easyloan.feature_borrower.domain.use_case.GetBorrower
import com.jecsdev.easyloan.feature_borrower.domain.use_case.GetBorrowers
import com.jecsdev.easyloan.feature_borrower.domain.use_case.UpdateBorrower
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Use cases module.
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {
    @Provides
    @Singleton
    fun provideBorrowerUseCases(repository: BorrowerRepositoryImplementation): BorrowerUseCases {
        return BorrowerUseCases(
            getBorrower = GetBorrower(repository),
            getBorrowers = GetBorrowers(repository),
            addBorrower = AddBorrower(repository),
            deleteBorrower = DeleteBorrower(repository),
            updateBorrower = UpdateBorrower(repository)
        )
    }
}