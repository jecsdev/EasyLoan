package com.jecsdev.easyloan.ui.composables.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.ui.theme.lightGrayColor


/**
 * Composable that show a card with an Icon only.
 */
@Composable
fun IconCard(painter: Painter) {
    Card(modifier = Modifier.size(80.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = lightGrayColor) {

        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            painter = painter,
            contentDescription = stringResource(R.string.loan_icon)
        )
    }
}