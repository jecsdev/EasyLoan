package com.jecsdev.easyloan.presentation.signin

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.utils.constants.firebaseClientId
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

/**
 * This clas manages the Google Authentication
 * @author John Campusano
 *
 * @param context Application Context
 * @param oneTapClient this is te sign in client
 */
class GoogleAuthClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth
    private val emptyString = context.getString(R.string.empty_string)

    /**
     * Handles the sign in
     */
    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        }catch(exception: Exception){
            exception.printStackTrace()
            if(exception is CancellationException) throw exception
            null
        }
        return result?.pendingIntent?.intentSender
    }

    /**
     * Manages the sign in from the intent
     * @param intent the intent for display the account sheet
     */
    suspend fun signInResultFromIntent(intent: Intent): SignInResult{
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        userName = displayName ?: emptyString,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        }catch (exception: Exception){
            exception.printStackTrace()
            if(exception is CancellationException) throw exception
            SignInResult(
                data = null,
                errorMessage = exception.message
            )
        }
    }

    /**
     * Handles when user sign out
     */
    suspend fun signOut(){
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        }catch (exception: Exception){
            exception.printStackTrace()
            if(exception is CancellationException) throw exception
        }
    }

    /**
     * Handles the builder request
     */
    private fun buildSignInRequest() : BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.Builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(firebaseClientId)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    /**
     * Retrieves the data once the user is signed in
     */
    fun getSignedUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            userName = displayName ?: emptyString,
            profilePictureUrl = photoUrl?.toString()
        )
    }
}