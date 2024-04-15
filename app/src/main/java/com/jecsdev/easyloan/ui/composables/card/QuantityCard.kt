package com.jecsdev.easyloan.ui.composables.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.ui.theme.lightGrayColor

/**
 * Represents the Composable to show the Loans quantity.
 * @param headerText active loans header.
 * @param quantity loans quantity.
 * @param modifier modifier to apply.
 */
@Composable
fun QuantityCard(headerText: String, quantity: Int, modifier: Modifier) {
    Card(
        modifier = modifier
            .width(176.dp)
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = lightGrayColor)
    ) {
        Column(
            modifier = modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = headerText,
                color = Color.Gray,
                fontSize = 16.sp
            )
            Text(
                text = quantity.toString(),
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun QuantityCardPreview() {
    val modifier = Modifier
    QuantityCard(headerText = stringResource(id = R.string.active_loans), quantity = 10, modifier = modifier)
}