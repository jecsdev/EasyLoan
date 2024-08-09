package com.jecsdev.easyloan.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jecsdev.easyloan.presentation.navigation.Destination.BorrowerDetails
import com.jecsdev.easyloan.presentation.navigation.Destination.BorrowersList
import com.jecsdev.easyloan.presentation.navigation.Destination.CreateBorrower
import com.jecsdev.easyloan.presentation.navigation.Destination.CreateLoan
import com.jecsdev.easyloan.presentation.navigation.Destination.Home
import com.jecsdev.easyloan.presentation.navigation.Destination.LogIn
import com.jecsdev.easyloan.ui.screens.borrower.BorrowerDetails
import com.jecsdev.easyloan.ui.screens.borrower.BorrowersListScreen
import com.jecsdev.easyloan.ui.screens.borrower.CreateBorrowerScreen
import com.jecsdev.easyloan.ui.screens.home.HomeScreen
import com.jecsdev.easyloan.ui.screens.loan.CreateLoanScreen
import com.jecsdev.easyloan.ui.screens.login.LogInScreen
import com.jecsdev.easyloan.ui.viewmodel.AuthViewModel
import com.jecsdev.easyloan.ui.viewmodel.BorrowerViewModel
import kotlinx.coroutines.launch

@Composable
fun NavGraph(
    context: Context,
    navController: NavHostController,
    authViewModel: AuthViewModel,
    borrowerViewModel: BorrowerViewModel
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    NavHost(navController = navController, startDestination = LogIn.route) {
        composable(LogIn.route) {
            val state by authViewModel.signInState.collectAsStateWithLifecycle()

            //Sign in user if there a signed user automatically.
            LaunchedEffect(key1 = Unit) {
                if (authViewModel.getSignedUser() != null) {
                    navController.navigate(Home.route)
                } else if (state.isSuccessful) {
                    navController.navigate(Home.route)
                    authViewModel.resetState()
                }
            }

            LogInScreen(state = state, onSignInClick = {
                lifeCycleOwner.lifecycleScope.launch {
                    authViewModel.signIn(context)
                }
            })
        }
        composable(Home.route) {
            HomeScreen(
                userData = authViewModel.getSignedUser(),
                onSignOut = {
                    lifeCycleOwner.lifecycleScope.launch {
                        authViewModel.signOut()
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
            })
        ) { navBackStackEntry ->
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