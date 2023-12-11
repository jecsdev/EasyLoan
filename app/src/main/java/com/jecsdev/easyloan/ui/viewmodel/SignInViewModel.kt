package com.jecsdev.easyloan.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jecsdev.easyloan.data.repository.AuthRepository
import com.jecsdev.easyloan.presentation.signin.SignInResult
import com.jecsdev.easyloan.ui.state.SignInState
import com.jecsdev.easyloan.utils.resources.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This class represents the Sign In ViewModel
 */
@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(){

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _signInState = Channel<SignInState>()
    val signInState = _signInState.receiveAsFlow()

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

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        authRepository.loginUser(email, password).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _signInState.send(SignInState(isSuccessful = true ))
                }
                is Resource.Loading -> {
                    _signInState.send(SignInState(isLoading = true))
                }
                is Resource.Error -> {

                    _signInState.send(SignInState(isError = result.message))
                }
            }

        }
    }
}