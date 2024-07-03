package com.jecsdev.easyloan.feature_authentication.repository

import com.jecsdev.easyloan.feature_authentication.data.model.UserData
import com.jecsdev.easyloan.presentation.signin.SignInResult

/**
 * Authentication Repository interface.
 */
interface AuthRepository {
    suspend fun googleSignIn(): SignInResult
    fun googleSignOut()
    fun getSignedInUser(): UserData?
    fun getUserId(): String?
}