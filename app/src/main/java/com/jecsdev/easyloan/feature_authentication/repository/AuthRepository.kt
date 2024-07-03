package com.jecsdev.easyloan.feature_authentication.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.jecsdev.easyloan.feature_authentication.data.model.UserData
import com.jecsdev.easyloan.presentation.signin.SignInResult
import com.jecsdev.easyloan.utils.resources.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Authentication Repository interface.
 */
interface AuthRepository {
    suspend fun googleSignIn(): SignInResult
    fun googleSignOut()
    fun getSignedInUser(): UserData?
    fun getUserId(): String?
}