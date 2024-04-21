package com.jecsdev.easyloan.ui.screens.debtors

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.presentation.navigation.Destination
import com.jecsdev.easyloan.ui.composables.card.DebtorCard
import com.jecsdev.easyloan.ui.composables.header.TitleHeader
import com.jecsdev.easyloan.ui.composables.textfield.SearchTextField
import com.jecsdev.easyloan.ui.theme.navyBlueColor


/**
 * Debtors list Screen composable.
 * @param navController Navigation controller which handles this transaction.
 * @author John Campusano.
 */
@Composable
fun DebtorsListScreen(navController: NavController?) {
    val searchResource = stringResource(R.string.search)
    val searchValue by rememberSaveable { mutableStateOf("") }

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { navigateToCreateDebtorScreen(navController) },
            containerColor = navyBlueColor,
            contentColor = Color.White
        ) {
            Icon(
                Icons.Filled.Add,
                stringResource(R.string.floating_action_button_add_debtor_description)
            )
        }
    }, containerColor = colorResource(id = R.color.phantom_gray_color)) {
        it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            TitleHeader(
                titleText = stringResource(id = R.string.debtorss_list),
                navController = navController
            )
            Spacer(modifier = Modifier.height(24.dp))
            SearchTextField(searchText = searchValue, labelString = searchResource,
                supportingTextLegend = stringResource(R.string.debtors_text_field_disclaimer), modifier = Modifier)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(10) {
                    DebtorCard()
                }
            }
        }
    }
}


/**
 * Debtors list Screen preview.
 */
@Composable
@Preview(showSystemUi = true)
fun DebtorsListScreenPreview() {
    DebtorsListScreen(null)
}

/**
 * Navigates to create debtor screen.
 * @param navController Navigation controller which handle this transaction.
 */
fun navigateToCreateDebtorScreen(navController: NavController?) {
    navController?.navigate(Destination.CreateDebtor.route)
}
