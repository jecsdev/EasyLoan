package com.jecsdev.easyloan.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.jecsdev.easyloan.utils.resources.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Authentication Repository interface.
 */
interface AuthRepository {
    fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>
    fun registerUser(email: String, password: String): Flow<Resource<AuthResult>>
    fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>>

}