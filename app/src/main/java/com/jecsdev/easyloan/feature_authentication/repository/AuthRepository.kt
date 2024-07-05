package com.jecsdev.easyloan.feature_authentication.repository

import android.content.Context
import com.jecsdev.easyloan.feature_authentication.data.model.UserData
import com.jecsdev.easyloan.presentation.signin.SignInResult

/**
 * Authentication Repository interface.
 */
interface AuthRepository {
    suspend fun googleSignIn(context: Context): SignInResult
    fun googleSignOut()
    fun getSignedInUser(): UserData?
    fun getUserId(): String?
}