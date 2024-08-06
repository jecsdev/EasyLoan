package com.jecsdev.easyloan.ui.screens.borrower

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.feature_borrower.data.model.Borrower
import com.jecsdev.easyloan.ui.composables.header.TitleHeader
import com.jecsdev.easyloan.ui.theme.lightGrayColor
import com.jecsdev.easyloan.ui.viewmodel.BorrowerViewModel

@Composable
fun BorrowerDetails(
    viewModel: BorrowerViewModel?,
    navController: NavController?,
    borrowerId: String?
) {
    val borrower = viewModel?.borrowerState?.collectAsState()?.value

    LaunchedEffect(Unit) {
        borrowerId?.let { viewModel?.getBorrower(it) }
    }

    if (borrower != null) {
        BorrowerDetailsContent(borrower = borrower, navController = navController)
    }
}

@Composable
fun BorrowerDetailsContent(borrower: Borrower?, navController: NavController?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp)
    ) {
        TitleHeader(
            titleText = stringResource(id = R.string.borrower_details),
            navController = navController
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            AsyncImage(
                model = borrower?.photo,
                contentDescription = stringResource(
                    R.string.selected_image_description
                ),
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = lightGrayColor),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(id = R.string.name),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp, start = 12.dp)
                    )
                    Text(
                        text = borrower?.name ?: "",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp, start = 12.dp)
                    )
                }
                HorizontalDivider()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = lightGrayColor),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(id = R.string.identification_number),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp, start = 12.dp)
                    )
                    Text(
                        text = borrower?.identificationNumber ?: "",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp, start = 12.dp)
                    )
                }
                HorizontalDivider()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = lightGrayColor),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(id = R.string.phone_number),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp, start = 12.dp)
                    )
                    Text(
                        text = borrower?.phone ?: "",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp, start = 12.dp)
                    )
                }
                HorizontalDivider()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = lightGrayColor),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(id = R.string.address),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp, start = 12.dp)
                    )
                    Text(
                        text = borrower?.address ?: "",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp, start = 12.dp)
                    )
                }
                HorizontalDivider()
            }
        }
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
        "8295588787",
        "Respaldo direccion no. 25",
        "profile pic"
    )
    BorrowerDetailsContent(borrower = borrower, navController = null)
}