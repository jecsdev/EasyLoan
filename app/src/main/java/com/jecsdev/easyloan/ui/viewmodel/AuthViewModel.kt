package com.jecsdev.easyloan.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jecsdev.easyloan.feature_authentication.data.model.UserData
import com.jecsdev.easyloan.feature_authentication.repository.AuthRepository
import com.jecsdev.easyloan.presentation.signin.SignInResult
import com.jecsdev.easyloan.ui.state.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel(){
    private val _authState = MutableStateFlow<SignInResult?>(null)
    val authState: StateFlow<SignInResult?> = _authState

    private val _state = MutableStateFlow(SignInState())
    val signInState = _state.asStateFlow()

    /**
     * Handles the result in Sign In
     * @param result this is the Sign In Result
     */
    fun onSignInResult(result: SignInResult){
        _state.update {signInState->
            signInState.copy(
                isSuccessful = result.data != null,
                isError = result.errorMessage
            )
        }
    }

    fun resetState(){
        _state.update { SignInState() }
    }

    fun signIn() {
       viewModelScope.launch {
           val result = authRepository.googleSignIn()
            _authState.value = result
       }
    }

    fun signOut() {
        authRepository.googleSignOut()
        _authState.value = null
    }

    fun getSignedUser(): UserData? = authRepository.getSignedInUser()

    fun getUserId(): String = authRepository.getUserId() ?: ""

}