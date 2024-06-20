package com.jecsdev.easyloan.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.presentation.navigation.Destination.BorrowersList
import com.jecsdev.easyloan.presentation.navigation.Destination.CreateBorrower
import com.jecsdev.easyloan.presentation.navigation.Destination.CreateLoan
import com.jecsdev.easyloan.presentation.navigation.Destination.Home
import com.jecsdev.easyloan.presentation.navigation.Destination.LogIn
import com.jecsdev.easyloan.presentation.signin.GoogleAuthClient
import com.jecsdev.easyloan.ui.screens.borrower.BorrowersListScreen
import com.jecsdev.easyloan.ui.screens.borrower.CreateBorrowerScreen
import com.jecsdev.easyloan.ui.screens.home.HomeScreen
import com.jecsdev.easyloan.ui.screens.loan.CreateLoanScreen
import com.jecsdev.easyloan.ui.screens.login.LogInScreen
import com.jecsdev.easyloan.ui.theme.EasyLoanTheme
import com.jecsdev.easyloan.ui.viewmodel.BorrowerViewModel
import com.jecsdev.easyloan.ui.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 * Application's Main Activity
 * @author John Campusano
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        GoogleAuthClient(
            context = applicationContext
        )
    }
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyLoanTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(R.color.phantom_gray_color)
                ) {

                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = LogIn.route) {
                        composable(LogIn.route) {

                            val state by viewModel.state.collectAsStateWithLifecycle()


                            LaunchedEffect(key1 = Unit) {
                                if (googleAuthUiClient.getSignedUser() != null) {
                                    navController.navigate(Home.route)
                                }
                            }

                            LaunchedEffect(key1 = state.isSuccessful) {
                                if (state.isSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        getString(R.string.session_started),
                                        Toast.LENGTH_LONG
                                    ).show()
                                    navController.navigate(Home.route)
                                    viewModel.resetState()
                                }
                            }

                            LogInScreen(state = state, onSignInClick = {
                                lifecycleScope.launch {
                                    googleAuthUiClient.signIn()
                                    viewModel.onSignInResult(result = googleAuthUiClient.getUserSigned())
                                }

                            })
                        }
                        composable(Home.route) {

                            HomeScreen(
                                userData = googleAuthUiClient.getSignedUser(),
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            getString(R.string.session_closed),
                                            Toast.LENGTH_LONG
                                        ).show()
                                        navController.popBackStack()
                                    }
                                },
                                navController = navController
                            )
                        }
                        composable(BorrowersList.route) {

                            BorrowersListScreen(navController = navController)
                        }
                        composable(CreateBorrower.route) {
                            val borrowerViewModel: BorrowerViewModel by viewModels()
                            CreateBorrowerScreen(viewModel = borrowerViewModel, navController = navController)
                        }
                        composable(CreateLoan.route) {

                            CreateLoanScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
