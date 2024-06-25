package com.jecsdev.easyloan.ui.screens.borrower

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.feature_borrower.data.model.Borrower
import com.jecsdev.easyloan.presentation.uihelpers.InputType
import com.jecsdev.easyloan.ui.composables.header.TitleHeader
import com.jecsdev.easyloan.ui.composables.textfield.SimpleTextField
import com.jecsdev.easyloan.ui.theme.navyBlueColor
import com.jecsdev.easyloan.ui.viewmodel.BorrowerViewModel
import kotlinx.coroutines.launch

/**
 * This is the borrower creation screen.
 */
@Composable
fun CreateBorrowerScreen(viewModel: BorrowerViewModel, navController: NavController?) {
    var name by rememberSaveable {
        mutableStateOf("")
    }
    var lastName by rememberSaveable {
        mutableStateOf("")
    }
    var identificationNumber by rememberSaveable {
        mutableStateOf("")
    }
    var address by rememberSaveable {
        mutableStateOf("")
    }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                coroutineScope.launch {
                    saveBorrower(
                        viewModel = viewModel,
                        borrower = Borrower(
                            id = null,
                            name = name,
                            lastName = lastName,
                            identificationNumber = identificationNumber,
                            address = address,
                            photo = ""
                        ), navController = navController
                    )
                }
            },
            containerColor = navyBlueColor,
            contentColor = Color.White
        ) {
            Icon(
                Icons.Filled.Save,
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
                titleText = stringResource(id = R.string.add_borrower),
                navController = navController
            )
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.borrower_icon),
                    contentDescription = stringResource(
                        R.string.selected_image_description
                    ),
                    modifier = Modifier
                        .height(120.dp)
                        .width(120.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(12.dp))
                SimpleTextField(
                    textTyped = name,
                    labelValue = stringResource(id = R.string.name),
                    onValueChange = { value -> name = value },
                    isSingleLine = true,
                    inputType = InputType.TEXT,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(12.dp))
                SimpleTextField(
                    textTyped = lastName,
                    labelValue = stringResource(id = R.string.last_name),
                    onValueChange = { value -> lastName = value },
                    isSingleLine = true,
                    inputType = InputType.TEXT,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(12.dp))
                SimpleTextField(
                    textTyped = identificationNumber,
                    labelValue = stringResource(id = R.string.identification_number),
                    onValueChange = { value -> identificationNumber = value },
                    isSingleLine = true,
                    inputType = InputType.TEXT,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(12.dp))
                SimpleTextField(
                    textTyped = address,
                    labelValue = stringResource(id = R.string.address),
                    onValueChange = { value -> address = value },
                    isSingleLine = true,
                    inputType = InputType.TEXT,
                    modifier = Modifier
                )
            }
        }
    }
}

/**
 * Create borrower Screen preview.
 */
@Composable
@Preview(showSystemUi = true)
fun CreateBorrowerScreenPreview() {
    val viewModel: BorrowerViewModel = hiltViewModel()
    CreateBorrowerScreen(viewModel = viewModel, navController = null)
}

/**
 * Stores the current borrower in database.
 */
suspend fun saveBorrower(
    viewModel: BorrowerViewModel,
    borrower: Borrower,
    navController: NavController?
) {
    viewModel.addBorrower(borrower)
    navController?.navigateUp()
}

