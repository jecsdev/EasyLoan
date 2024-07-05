package com.jecsdev.easyloan.feature_authentication.data.repository

import android.app.Activity
import android.content.Context
import com.jecsdev.easyloan.feature_authentication.data.model.UserData
import com.jecsdev.easyloan.feature_authentication.repository.AuthRepository
import com.jecsdev.easyloan.presentation.signin.GoogleAuthClient
import com.jecsdev.easyloan.presentation.signin.SignInResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImplementation @Inject constructor(private val googleAuthClient: GoogleAuthClient) : AuthRepository {
    private var currentUserData: UserData? = null
    override suspend fun googleSignIn(context: Context): SignInResult {
        return withContext(Dispatchers.IO) {
            try {
                googleAuthClient.signIn(context)

                val signInResult = googleAuthClient.getUserSigned()
                if (signInResult.data != null) {
                    currentUserData = signInResult.data
                }

                signInResult
            } catch (e: Exception) {
                SignInResult(data = null, errorMessage = e.message)
            }
        }
    }

    override fun googleSignOut() {
        googleAuthClient.signOut()
        currentUserData = null
    }

    override fun getSignedInUser(): UserData? {
        return currentUserData ?: googleAuthClient.getSignedUser().also {data ->
            currentUserData = data
        }
    }

    override fun getUserId(): String? {
        return currentUserData?.userId
    }

}