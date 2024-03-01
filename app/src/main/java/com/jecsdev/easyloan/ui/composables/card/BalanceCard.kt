package com.jecsdev.easyloan.ui.composables.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jecsdev.easyloan.ui.theme.lightGrayColor
import com.jecsdev.easyloan.utils.formatters.StringFormatter

/**
 * Composable to display balance cards.
 */
@Composable
fun BalanceCard(headerText: String, balance: Double,modifier: Modifier){
    Card(
        modifier = modifier
            .width(176.dp)
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = lightGrayColor
    ) {
        Column(
            modifier = modifier
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = headerText,
                color = Color.Gray,
                fontSize = 16.sp
            )
            Text(
                text = StringFormatter.amountFormatter(balance),
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}