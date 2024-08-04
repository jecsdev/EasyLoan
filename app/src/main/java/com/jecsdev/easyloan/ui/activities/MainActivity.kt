package com.jecsdev.easyloan.ui.activities

import  android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.presentation.navigation.Destination.*
import com.jecsdev.easyloan.ui.screens.borrower.BorrowerDetails
import com.jecsdev.easyloan.ui.screens.borrower.BorrowersListScreen
import com.jecsdev.easyloan.ui.screens.borrower.CreateBorrowerScreen
import com.jecsdev.easyloan.ui.screens.home.HomeScreen
import com.jecsdev.easyloan.ui.screens.loan.CreateLoanScreen
import com.jecsdev.easyloan.ui.screens.login.LogInScreen
import com.jecsdev.easyloan.ui.theme.EasyLoanTheme
import com.jecsdev.easyloan.ui.theme.phantomGrayColor
import com.jecsdev.easyloan.ui.viewmodel.AuthViewModel
import com.jecsdev.easyloan.ui.viewmodel.BorrowerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


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
                        NavHost(navController = navController, startDestination = LogIn.route) {
                            composable(LogIn.route) {
                                val state by viewModel.signInState.collectAsStateWithLifecycle()
                                LaunchedEffect(key1 = Unit) {
                                    if (viewModel.getSignedUser() != null) {
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
                                        viewModel.signIn(this@MainActivity)
                                    }
                                })
                            }
                            composable(Home.route) {
                                HomeScreen(
                                    userData = viewModel.getSignedUser(),
                                    onSignOut = {
                                        lifecycleScope.launch {
                                            viewModel.signOut()
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
                            composable(
                                route = BorrowersList.route + "/{userId}",
                                arguments = listOf(navArgument("userId") {
                                    type = NavType.StringType
                                })) { navBackStackEntry ->
                                val userId = navBackStackEntry.arguments?.getString("userId")
                                BorrowersListScreen(
                                    viewModel = borrowerViewModel,
                                    navController = navController,
                                    userId = userId
                                )
                            }
                            composable(CreateBorrower.route) {
                                CreateBorrowerScreen(
                                    viewModel = borrowerViewModel,
                                    navController = navController
                                )
                            }
                            composable(CreateLoan.route) {
                                CreateLoanScreen(navController = navController)
                            }
                            composable(
                                route = BorrowerDetails.route + "/{borrowerId}",
                                arguments = listOf(navArgument("borrowerId") {
                                    type = NavType.StringType
                                })
                            ) { navBackStackEntry ->
                                val borrowerId = navBackStackEntry.arguments?.getString("borrowerId")
                                BorrowerDetails(
                                    viewModel = borrowerViewModel,
                                    navController = navController,
                                    borrowerId = borrowerId
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
