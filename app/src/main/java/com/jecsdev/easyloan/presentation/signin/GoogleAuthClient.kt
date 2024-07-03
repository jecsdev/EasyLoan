package com.jecsdev.easyloan.presentation.signin

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.feature_authentication.data.model.UserData
import com.jecsdev.easyloan.utils.constants.firebaseClientId
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import javax.inject.Inject

/**
 * This clas manages the Google Authentication
 * @author John Campusano
 *
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
     * Handles the sign in
     */
    suspend fun signIn() {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
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
                    context = context
                )
                val credential: Credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken

                signInResult(googleIdToken)
                Log.i("Login", googleIdToken)

                Toast.makeText(context, "logged in", Toast.LENGTH_SHORT).show()
            } catch (exception: GetCredentialException) {
                Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
            } catch (exception: GoogleIdTokenParsingException) {
                Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
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