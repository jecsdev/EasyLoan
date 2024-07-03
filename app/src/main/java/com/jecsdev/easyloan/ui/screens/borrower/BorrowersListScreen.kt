package com.jecsdev.easyloan.ui.screens.borrower

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.feature_authentication.data.model.UserData
import com.jecsdev.easyloan.presentation.navigation.Destination
import com.jecsdev.easyloan.ui.composables.card.BorrowerCard
import com.jecsdev.easyloan.ui.composables.header.TitleHeader
import com.jecsdev.easyloan.ui.composables.textfield.SearchTextField
import com.jecsdev.easyloan.ui.state.BorrowerState
import com.jecsdev.easyloan.ui.theme.navyBlueColor
import com.jecsdev.easyloan.ui.viewmodel.BorrowerViewModel


/**
 * Borrowers list Screen composable.
 * @param viewModel BorrowerViewModel instance.
 * @param navController Navigation controller which handles this transaction.
 * @author John Campusano.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BorrowersListScreen(viewModel: BorrowerViewModel,
                        userData: UserData?,
                        navController: NavController?) {
    val searchResource = stringResource(R.string.search)
    val searchValue by rememberSaveable { mutableStateOf("") }
    val state = viewModel.state.value
    val showShimmer = remember { mutableStateOf(true) }
    val alpha by animateFloatAsState(
        targetValue = if (!showShimmer.value) 0f else 10f, animationSpec = tween(2000),
        label = ""
    )

    var key by remember { mutableIntStateOf(0) }
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { navigateToCreateBorrowerScreen(navController) },
            containerColor = navyBlueColor,
            contentColor = Color.White
        ) {
            Icon(
                Icons.Filled.Add,
                stringResource(R.string.floating_action_button_add_borrower_description)
            )
        }
    }, containerColor = colorResource(id = R.color.phantom_gray_color)) { paddingValues ->
        paddingValues.calculateBottomPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            TitleHeader(
                titleText = stringResource(id = R.string.borrowers_list),
                navController = navController
            )
            Spacer(modifier = Modifier.height(24.dp))
            when (state) {
                is BorrowerState.Loading -> {
                    LazyColumn {
                        items(8) {
                            BorrowerCard(null, showShimmer = showShimmer.value, modifier = Modifier)
                        }
                    }
                }

                is BorrowerState.Success -> {
                    LaunchedEffect(state.borrowers.size) {
                        key++
                    }
                    AnimatedVisibility(
                        visible = state.borrowers.isNotEmpty(),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Column() {
                            SearchTextField(
                                searchText = searchValue,
                                labelString = searchResource,
                                supportingTextLegend = stringResource(R.string.borrower_text_field_disclaimer),
                                modifier = Modifier
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            LazyColumn {
                                items(state.borrowers) { borrower ->
                                    BorrowerCard(
                                        borrower, false, modifier = Modifier
                                            .animateEnterExit()
                                            .alpha(alpha)
                                    )
                                }
                            }
                        }
                    }
                }

                is BorrowerState.Editing -> TODO()
                BorrowerState.Empty -> TODO()
                is BorrowerState.ReadyToSave -> TODO()
            }

        }
    }
}


/**
 * Borrowers list Screen preview.
 */
@Composable
@Preview(showSystemUi = true)
fun BorrowersListScreenPreview() {
    val viewModel: BorrowerViewModel = hiltViewModel()
    BorrowersListScreen(viewModel, null, null)
}

/**
 * Navigates to create borrower's screen.
 * @param navController Navigation controller which handle this transaction.
 */
fun navigateToCreateBorrowerScreen(navController: NavController?) {
    navController?.navigate(Destination.CreateBorrower.route)
}
