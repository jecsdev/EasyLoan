package com.jecsdev.easyloan.ui.screens.loan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.presentation.navigation.Destination
import com.jecsdev.easyloan.ui.composables.header.TitleHeader
import com.jecsdev.easyloan.ui.composables.textfield.SearchTextField
import com.jecsdev.easyloan.ui.theme.navyBlueColor


/**
 * Create Loan Screen composable.
 * @param navController navigation controller.
 */
@Composable
fun CreateLoanScreen(navController: NavController?) {
    val searchResource = stringResource(R.string.search)
    val searchValue by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        TitleHeader(
            titleText = stringResource(R.string.loan_creation),
            navController = navController
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SearchTextField(
                searchText = searchValue,
                labelString = searchResource,
                supportingTextLegend = stringResource(R.string.debtors_text_field_disclaimer),
                modifier = Modifier
                    .width(80.dp)
                    .weight(1f)
            )
            Button(
                modifier = Modifier
                    .width(72.dp)
                    .height(64.dp),
                onClick = {
                    navController?.let { navigation ->
                        navigateToCreteDebtorScreen(
                            navigation
                        )
                    }
                },
                colors = ButtonColors(
                    containerColor = navyBlueColor,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent
                )
            ) {
                Icon(
                    Icons.Filled.Add,
                    stringResource(R.string.add_customer),
                )
            }
        }
    }
}

/**
 * Composable's preview.
 */
@Composable
@Preview(showSystemUi = true)
fun CreateLoanScreenPreview() {
    CreateLoanScreen(navController = null)
}

/**
 * Navigates to create debtor's screen if necessary.
 * @param navController navigation controller for this navigation.
 */
fun navigateToCreteDebtorScreen(navController: NavController?) {
    navController?.navigate(Destination.CreateDebtor.route)
}