package com.jecsdev.easyloan.ui.screens.borrower

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.feature_borrower.data.model.Borrower

import com.jecsdev.easyloan.ui.composables.header.TitleHeader
import com.jecsdev.easyloan.ui.viewmodel.BorrowerViewModel

@Composable
fun BorrowerDetails(
    viewModel: BorrowerViewModel?,
    navController: NavController?,
    borrowerId: String?
) {
    val borrower = viewModel?.borrowerState?.collectAsState()?.value
    val id = borrowerId ?: ""
    LaunchedEffect(Unit) {
        viewModel?.getBorrower(id)
    }

    if (borrower != null) {
        BorrowerDetailsContent(borrower = borrower, navController = navController)
    }
}

@Composable
fun BorrowerDetailsContent(borrower: Borrower, navController: NavController?){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        TitleHeader(
            titleText = stringResource(id = R.string.add_borrower),
            navController = navController
        )
        Spacer(modifier = Modifier.height(24.dp))
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
        Text(
            text = borrower.name
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = borrower.identificationNumber
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = borrower.address
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun BorrowerDetailsPreview() {
    val borrower = Borrower(
        "0000",
        "000000",
        "John Doe",
        "000000000",
        "address",
        "profile pic")
    BorrowerDetailsContent(borrower = borrower, navController = null)
}