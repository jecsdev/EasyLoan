package com.jecsdev.easyloan.ui.screens.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.data.entity.user.UserData
import com.jecsdev.easyloan.presentation.navigation.Destination
import com.jecsdev.easyloan.ui.composables.card.BalanceCard
import com.jecsdev.easyloan.ui.composables.card.CustomerTransactionResumeCard
import com.jecsdev.easyloan.ui.composables.card.IconCard

/**
 * DashboardScreen view.
 */
@Composable
fun DashboardScreen(
    userData: UserData?,
    onSignOut: () -> Unit,
    navController: NavController?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (userData?.profilePictureUri != null && userData.userName.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(3f)
                ) {
                    AsyncImage(
                        model = userData.profilePictureUri,
                        contentDescription = stringResource(R.string.profile_picture),
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.greeting_with_comma) +
                                " " + userData.userName,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            IconButton(
                onClick = onSignOut,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(48.dp)
            ) {

                Icon(
                    modifier = Modifier
                        .padding(start = 4.dp, top = 4.dp),
                    painter = painterResource(id = R.drawable.power_icon_24),
                    contentDescription = stringResource(id = R.string.power_icon)
                )
            }

        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.account_status),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            BalanceCard(
                headerText = stringResource(R.string.available_amount),
                balance = 20000.00, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            BalanceCard(
                headerText = stringResource(R.string.credited_amount),
                balance = 150000.00, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.features),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconCard(
                    painter = painterResource(id = R.drawable.loan_icon_action),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.create),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconCard(
                    painter = painterResource(id = R.drawable.deposit_icon),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.deposit),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconCard(painter = painterResource(id = R.drawable.customer_icon),
                    modifier = Modifier.clickable {
                        navController?.let { navigation ->
                            navigateToCustomersScreen(
                                navigation
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.customers),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.recent_transactions),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(5) {
                CustomerTransactionResumeCard()
            }
        }
    }
}

/*
 * Dashboard Screen preview with dummy data.
 */
@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(
        userData = UserData(
            userName = stringResource(R.string.john_doe),
            userId = stringResource(R.string.sample_number),
            profilePictureUri = stringResource(id = R.string.empty_string)
        ),
        onSignOut = {},
        navController = null
    )
}

/**
 * Handles navigation to Customers list screen.
 * @param navController navigation controller,
 */
fun navigateToCustomersScreen(navController: NavController) {
    navController.navigate(Destination.CustomersList.route)
}
