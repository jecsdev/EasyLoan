package com.jecsdev.easyloan.ui.composables.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.jecsdev.easyloan.ui.composables.shimmer.shimmer

/**
 * Custom text composable.
 * @param text String value to be displayed.
 * @param fontSize TextUnit value to set the font size.
 * @param fontWeight FontWeight value to set the font weight.
 * @param modifier Modifier value to set the modifier.
 * @param showShimmer Boolean value to show or hide the shimmer effect.
 */
@Composable
fun CustomText(
    text: String?,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    modifier: Modifier,
    showShimmer: Boolean
) {
    if (text == null) {
        Column {
            Box(
                modifier = modifier
                    .width(240.dp)
                    .height(4.dp)
                    .background(shimmer(targetValue = 1300f, showShimmer = showShimmer))
            )
            Box(
                modifier = modifier
                    .width(240.dp)
                    .height(4.dp)
                    .background(shimmer(targetValue = 1300f, showShimmer = showShimmer))
            )
        }



    } else {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            modifier = modifier
        )
    }
}

/**
 * Custom text preview.
 */
@Composable
@Preview(showSystemUi = true)
fun CustomTextPreview() {
    CustomText(
        text = "Hello",
        fontSize = TextUnit.Unspecified,
        fontWeight = null,
        modifier = Modifier,
        showShimmer = true
    )
}