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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.feature_borrower.data.model.Borrower
import com.jecsdev.easyloan.ui.theme.lightGrayColor

/**
 * Borrower's information card.
 */
@Composable
fun BorrowerCard(borrower: Borrower?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = lightGrayColor),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    modifier = Modifier.size(64.dp),
                    painter = painterResource(id = R.drawable.borrower_icon),
                    contentDescription = stringResource(
                        R.string.borrower_card_icon
                    )
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                borrower?.let { borrower ->
                    Text(
                        text = borrower.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                borrower?.let { borrower ->
                    Text(
                        text = borrower.lastName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                borrower?.let { borrower ->
                    Text(
                        text = borrower.identificationNumber,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
    Spacer(Modifier.height(4.dp))
}

@Composable
@Preview(showSystemUi = true)
fun BorrowerCardPreview() {
    BorrowerCard(borrower = Borrower(
        "100",
        "John",
        "Doe",
        "0001-00052545-8",
        "Calle demo 123",
        ""))
}