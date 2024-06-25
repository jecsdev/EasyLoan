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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.presentation.navigation.Destination
import com.jecsdev.easyloan.ui.composables.card.BorrowerCard
import com.jecsdev.easyloan.ui.composables.header.TitleHeader
import com.jecsdev.easyloan.ui.composables.textfield.SearchTextField
import com.jecsdev.easyloan.ui.composables.textfield.SimpleTextField
import com.jecsdev.easyloan.ui.theme.navyBlueColor
import com.jecsdev.easyloan.presentation.uihelpers.InputType
import com.jecsdev.easyloan.ui.composables.dropdown.DropDown


/**
 * Create Loan Screen composable.
 * @param navController navigation controller.
 */
@Composable
fun CreateLoanScreen(navController: NavController?) {
    val searchResource = stringResource(R.string.search)
    val searchValue by rememberSaveable { mutableStateOf("") }
    var loanAmount by rememberSaveable {
        mutableStateOf("")
    }
    var selectedOption by rememberSaveable {
        mutableStateOf("")
    }
    var interestRate by rememberSaveable {
        mutableStateOf("")
    }
    val demoDueOptions = listOf("Semanal", "Quincenal", "Mensual")
    val demoDateList = listOf("1","2","3","4","5","6","7","8","9")
    val demoDatesOptions = listOf("Días", "Meses", "Años")

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
                supportingTextLegend = stringResource(R.string.borrower_text_field_disclaimer),
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
                        navigateToCreateBorrowerScreen(
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
        BorrowerCard(null, false, Modifier)
        SimpleTextField(
            textTyped = loanAmount,
            labelValue = stringResource(id = R.string.dummy_ammount),
            onValueChange = { value -> loanAmount = value },
            isSingleLine = true, inputType = InputType.NUMBER,
            modifier = Modifier.fillMaxWidth()
        )
        DropDown(label = stringResource(R.string.select_due),
           options = demoDueOptions,  onOptionSelected = { dueSelected -> selectedOption = dueSelected},
            modifier = Modifier)
        SimpleTextField(
            textTyped = interestRate,
            labelValue = stringResource(R.string.interest_rate),
            onValueChange = { value -> interestRate = value },
            isSingleLine = true,
            inputType = InputType.NUMBER,
            modifier = Modifier.fillMaxWidth()
        )
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            DropDown(label = stringResource(R.string.select_period_number),
                options = demoDateList,  onOptionSelected = { dueSelected -> selectedOption = dueSelected},
                modifier = Modifier.weight(1f))
            DropDown(label = stringResource(R.string.select_period),
                options = demoDatesOptions,  onOptionSelected = { dueSelected -> selectedOption = dueSelected},
                modifier = Modifier.weight(1f))

        }
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            SimpleTextField(
                textTyped = interestRate,
                labelValue = stringResource(R.string.loan_start_date),
                onValueChange = { value -> interestRate = value },
                isSingleLine = true,
                inputType = InputType.TEXT,
                modifier = Modifier.weight(1f)
            )
            SimpleTextField(
                textTyped = interestRate,
                labelValue = stringResource(R.string.loan_end_date),
                onValueChange = { value -> interestRate = value },
                isSingleLine = true, inputType = InputType.TEXT,
                modifier = Modifier.weight(1f))

        }
        Spacer(modifier = Modifier.height(4.dp))
        Button(onClick = { /*Pending to add function*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            colors = ButtonColors(
                containerColor = navyBlueColor,
                contentColor = Color.White,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            )) {
            Text(text = stringResource(R.string.create))
        }
    }
}

/**
 * Composable preview.
 */
@Composable
@Preview(showSystemUi = true)
fun CreateLoanScreenPreview() {
    CreateLoanScreen(navController = null)
}

/**
 * Navigates to create borrower's screen if necessary.
 * @param navController navigation controller for this navigation.
 */
fun navigateToCreateBorrowerScreen(navController: NavController?) {
    navController?.navigate(Destination.CreateBorrower.route)
}