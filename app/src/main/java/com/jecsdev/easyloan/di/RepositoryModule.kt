package com.jecsdev.easyloan.di

import com.google.firebase.firestore.FirebaseFirestore
import com.jecsdev.easyloan.feature_borrower.domain.repository.BorrowerRepository
import com.jecsdev.easyloan.feature_borrower.data.repository.BorrowerRepositoryImplementation

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Handles the dependency injection for Repository modules.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesBorrowerRepositoryImplementation(firestore: FirebaseFirestore): BorrowerRepository {
        return BorrowerRepositoryImplementation(firestore)
    }
}