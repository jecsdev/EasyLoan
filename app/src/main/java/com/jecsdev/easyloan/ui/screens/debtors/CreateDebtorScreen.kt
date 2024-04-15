package com.jecsdev.easyloan.ui.screens.debtors

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.ui.composables.header.TitleHeader
import com.jecsdev.easyloan.ui.theme.brownGrayColor
import com.jecsdev.easyloan.ui.theme.ghostColor
import com.jecsdev.easyloan.ui.theme.navyBlueColor

/**
 * This is the debtor creation screen.
 */
@Composable
fun CreateDebtorScreen(navController: NavController?) {
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
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { saveDebtor(navController) },
            containerColor = navyBlueColor,
            contentColor = Color.White
        ) {
            Icon(
                Icons.Filled.Save,
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
                titleText = stringResource(id = R.string.add_debtor),
                navController = navController
            )
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.debtor_icon),
                    contentDescription = stringResource(
                        R.string.selected_image_description
                    ),
                    modifier = Modifier
                        .height(120.dp)
                        .width(120.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(12.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp)
                        .background(color = Color.Transparent),
                    value = name,
                    colors = TextFieldDefaults.colors(
                        cursorColor = navyBlueColor,
                        disabledLabelColor = navyBlueColor,
                        focusedIndicatorColor = navyBlueColor,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = ghostColor,
                        unfocusedContainerColor = ghostColor
                    ),
                    onValueChange = { value -> name = value },
                    label = { Text(text = stringResource(id = R.string.name), color = brownGrayColor) },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp)
                        .background(color = Color.Transparent),
                    value = lastName,
                    colors = TextFieldDefaults.colors(
                        cursorColor = navyBlueColor,
                        disabledLabelColor = navyBlueColor,
                        focusedIndicatorColor = navyBlueColor,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = ghostColor,
                        unfocusedContainerColor = ghostColor
                    ),
                    onValueChange = { value -> lastName = value },
                    label = { Text(text = stringResource(id = R.string.last_name), color = brownGrayColor) },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp)
                        .background(color = Color.Transparent),
                    value = identificationNumber,
                    colors = TextFieldDefaults.colors(
                        cursorColor = navyBlueColor,
                        disabledLabelColor = navyBlueColor,
                        focusedIndicatorColor = navyBlueColor,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = ghostColor,
                        unfocusedContainerColor = ghostColor
                    ),
                    onValueChange = { value -> identificationNumber = value },
                    label = { Text(text = stringResource(id = R.string.identification_number), color = brownGrayColor) },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp)
                        .background(color = Color.Transparent),
                    value = address,
                    colors = TextFieldDefaults.colors(
                        cursorColor = navyBlueColor,
                        disabledLabelColor = navyBlueColor,
                        focusedIndicatorColor = navyBlueColor,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = ghostColor,
                        unfocusedContainerColor = ghostColor
                    ),
                    onValueChange = { value -> address = value },
                    label = { Text(text = stringResource(id = R.string.address), color = brownGrayColor) },
                    singleLine = false
                )
            }
        }
    }
}

/**
 * Create debtor Screen preview.
 */
@Composable
@Preview(showSystemUi = true)
fun CreateDebtorScreenPreview() {
    CreateDebtorScreen(navController = null)
}

/**
 * Stores the current debtor in database.
 */
fun saveDebtor(navController: NavController?){
    navController?.popBackStack()
}

