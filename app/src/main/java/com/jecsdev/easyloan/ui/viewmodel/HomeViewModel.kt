package com.jecsdev.easyloan.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.jecsdev.easyloan.ui.state.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()
}