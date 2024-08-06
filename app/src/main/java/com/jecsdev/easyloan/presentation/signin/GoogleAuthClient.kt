package com.jecsdev.easyloan.presentation.signin

import android.content.Context
import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.feature_authentication.data.model.UserData
import com.jecsdev.easyloan.utils.constants.ExceptionConstants.GOOGLE_ID_TOKEN_PARSING_EXCEPTION_TAG
import com.jecsdev.easyloan.utils.constants.ExceptionConstants.LOGIN_TAG
import com.jecsdev.easyloan.utils.constants.ExceptionConstants.SIGN_IN_EXCEPTION_TAG
import com.jecsdev.easyloan.utils.constants.firebaseClientId
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import javax.inject.Inject

/**
 * This clas manages the Google Authentication
 * @author John Campusano
 * @param context Application Context
 */
class GoogleAuthClient @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val auth = Firebase.auth
    private val emptyString = context.getString(R.string.empty_string)
    private val credentialManager = CredentialManager.create(context)
    private lateinit var signInResult: SignInResult

    /**
     * Handles the sign in from Google Login.
     * @param activityContext Activity Context.
     */
    suspend fun signIn(activityContext: Context) {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(firebaseClientId)
            .setNonce("")
            .build()
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
        coroutineScope {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = activityContext
                )
                val credential: Credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken

                signInResult(googleIdToken)
                Log.i(LOGIN_TAG, googleIdToken)
            } catch (exception: Exception) {
                Log.i(SIGN_IN_EXCEPTION_TAG, exception.message.toString())
                signInResult = SignInResult(data = null, errorMessage = exception.message)
            } catch (exception: GoogleIdTokenParsingException) {
                Log.i(GOOGLE_ID_TOKEN_PARSING_EXCEPTION_TAG, exception.message.toString())
                signInResult = SignInResult(data = null, errorMessage = exception.message)
            }
        }
    }

    /**
     * Manages the sign in from the Credential manager
     * @param idToken token received from credentials result.
     */
    private suspend fun signInResult(idToken: String) {
        val googleCredentials = GoogleAuthProvider.getCredential(idToken, null)
        try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            signInResult = SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        userName = displayName ?: emptyString,
                        profilePictureUri = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (exception: Exception) {
            exception.printStackTrace()
            if (exception is CancellationException) throw exception
            signInResult = SignInResult(
                data = null,
                errorMessage = exception.message
            )
        }
    }

    /**
     * Obtains the sign in result from Google Login.
     * @return Retrieve the sign in result from login performed.
     */
    fun getUserSigned(): SignInResult {
        return signInResult
    }

    /**
     * Handles when user sign out
     */
    fun signOut() {
        try {
            auth.signOut()
        } catch (exception: Exception) {
            exception.printStackTrace()
            if (exception is CancellationException) throw exception
        }
    }

    /**
     * Retrieves the data once the user is signed in
     */
    fun getSignedUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            userName = displayName ?: emptyString,
            profilePictureUri = photoUrl?.toString()
        )
    }
}