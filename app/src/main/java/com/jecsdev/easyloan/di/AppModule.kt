package com.jecsdev.easyloan.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jecsdev.easyloan.data.repository.AuthRepository
import com.jecsdev.easyloan.data.repository.AuthRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Handles the dependency injection from whole app
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun providesRepositoryImplementation(firebaseAuth: FirebaseAuth) : AuthRepository{
        return AuthRepositoryImplementation(firebaseAuth)
    }
}