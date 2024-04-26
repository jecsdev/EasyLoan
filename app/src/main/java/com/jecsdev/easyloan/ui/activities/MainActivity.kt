package com.jecsdev.easyloan.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import com.google.android.gms.auth.api.identity.Identity
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.presentation.navigation.Destination.*
import com.jecsdev.easyloan.presentation.signin.GoogleAuthClient
import com.jecsdev.easyloan.ui.screens.borrower.CreateBorrowerScreen
import com.jecsdev.easyloan.ui.screens.borrower.BorrowersListScreen
import com.jecsdev.easyloan.ui.screens.home.HomeScreen
import com.jecsdev.easyloan.ui.screens.loan.CreateLoanScreen
import com.jecsdev.easyloan.ui.screens.login.LogInScreen
import com.jecsdev.easyloan.ui.theme.EasyLoanTheme
import com.jecsdev.easyloan.ui.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 * Application's Main Activity
 * @author John Campusano
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Set Google Authentication client
    private val googleAuthUiClient by lazy {
        GoogleAuthClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyLoanTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(R.color.phantom_gray_color)
                ) {
                    // Navigation Host
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = LogIn.route) {
                        composable(LogIn.route) {

                            val state by viewModel.state.collectAsStateWithLifecycle()

                            // Check's if user's session is active
                            LaunchedEffect(key1 = Unit) {
                                if (googleAuthUiClient.getSignedUser() != null) {
                                    navController.navigate(Home.route)
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult =
                                                googleAuthUiClient.signInResultFromIntent(
                                                    intent = result.data ?: return@launch
                                                )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )
                            // Handles The sign in successfully
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
                            // Sign In Screen
                            LogInScreen(state = state, onSignInClick = {
                                lifecycleScope.launch {
                                    val signInIntentSender = googleAuthUiClient.signIn()
                                    launcher.launch(
                                        IntentSenderRequest.Builder(
                                            signInIntentSender ?: return@launch
                                        ).build()
                                    )
                                }
                            })
                        }
                        composable(Home.route) {
                            //Home screen
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
                            // Borrowers list screen
                            BorrowersListScreen(navController = navController)
                        }
                        composable(CreateBorrower.route){
                            //Create Borrowers screen
                            CreateBorrowerScreen(navController = navController)
                        }
                        composable(CreateLoan.route){
                            //Create Loan Screen
                            CreateLoanScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
