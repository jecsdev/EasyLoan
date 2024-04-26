package com.jecsdev.easyloan.ui.composables.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.ui.theme.lightGrayColor

/**
 * Composable to show the borrower transaction resume.
 */
@Composable
@Preview
fun BorrowerTransactionsResumeCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = lightGrayColor),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Image(
                    modifier = Modifier.size(64.dp),
                    painter = painterResource(id = R.drawable.borrower_icon),
                    contentDescription = stringResource(
                        R.string.borrower_card_icon
                    )
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "Jane Doe", fontSize = 12.sp
                )
                Text(
                    text = "8 de marzo de 2022",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column {
                Text(
                    text = "+$5000.00",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
    Spacer(Modifier.height(8.dp))
}