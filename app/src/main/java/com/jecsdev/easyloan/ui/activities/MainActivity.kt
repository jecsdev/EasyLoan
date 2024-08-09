package com.jecsdev.easyloan.ui.activities

import  android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.presentation.navigation.NavGraph
import com.jecsdev.easyloan.ui.theme.EasyLoanTheme
import com.jecsdev.easyloan.ui.theme.phantomGrayColor
import com.jecsdev.easyloan.ui.viewmodel.AuthViewModel
import com.jecsdev.easyloan.ui.viewmodel.BorrowerViewModel
import dagger.hilt.android.AndroidEntryPoint



/**
 * Application's Main Activity
 * @author John Campusano
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: AuthViewModel by viewModels()
    private val borrowerViewModel: BorrowerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyLoanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(R.color.phantom_gray_color),
                ) {
                    val systemUiController = rememberSystemUiController()
                    systemUiController.setStatusBarColor(phantomGrayColor)

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .systemBarsPadding()
                            .padding(top = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val navController = rememberNavController()
                        NavGraph(context = this@MainActivity,
                            navController = navController,
                            authViewModel = viewModel,
                            borrowerViewModel = borrowerViewModel)
                    }
                }
            }
        }
    }
}
