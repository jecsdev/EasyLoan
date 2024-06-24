package com.jecsdev.easyloan.ui.composables.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.jecsdev.easyloan.ui.composables.shimmer.shimmer

@Composable
fun CustomText(
    text: String?,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    modifier: Modifier
) {
    if (text != null) {
        Text(text = text, fontSize = fontSize, fontWeight = fontWeight)
    } else {
        Box(
            modifier.background(shimmer(
            targetValue = 1300f,
            showShimmer = true
        )).wrapContentWidth())
    }
}