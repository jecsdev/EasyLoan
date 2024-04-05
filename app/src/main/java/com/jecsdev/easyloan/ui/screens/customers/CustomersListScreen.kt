package com.jecsdev.easyloan.ui.screens.customers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.presentation.navigation.Destination
import com.jecsdev.easyloan.ui.composables.card.CustomerCard
import com.jecsdev.easyloan.ui.composables.header.TitleHeader
import com.jecsdev.easyloan.ui.theme.lightGrayColor
import com.jecsdev.easyloan.ui.theme.navyBlueColor

/**
 * Customers list Screen composable.
 * @param navController Navigation controller which handles this transaction.
 * @author John Campusano.
 */
@Composable
fun CustomersListScreen(navController: NavController?) {
    val searchResource = stringResource(R.string.search)
    var searchValue by rememberSaveable { mutableStateOf("") }

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { navigateToCreateCustomerScreen(navController) },
            containerColor = navyBlueColor,
            contentColor = Color.White
        ) {
            Icon(
                Icons.Filled.Add,
                stringResource(R.string.floating_action_button_add_customer_description)
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
                titleText = stringResource(id = R.string.customers_list),
                navController = navController
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp)
                    .background(color = Color.Transparent),
                value = searchValue,
                colors = TextFieldDefaults.colors(
                    cursorColor = lightGrayColor,
                    disabledLabelColor = navyBlueColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = lightGrayColor,
                    unfocusedContainerColor = lightGrayColor
                ),
                onValueChange = { value -> searchValue = value },
                label = { Text(searchResource) },
                supportingText = {
                    Text(stringResource(R.string.customers_text_field_disclaimer), color = colorResource(
                        id = R.color.dark_gray_color2
                    ))
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = stringResource(R.string.search_text_field_icon)
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(10) {
                    CustomerCard()
                }
            }
        }
    }
}


/**
 * Customer list Screen preview.
 */
@Composable
@Preview(showSystemUi = true)
fun CustomersListScreenPreview() {
    CustomersListScreen(null)
}

/**
 * Navigates to create customer screen.
 * @param navController Navigation controller which handle this transaction.
 */
fun navigateToCreateCustomerScreen(navController: NavController?) {
    navController?.navigate(Destination.CreateCustomer.route)
}
