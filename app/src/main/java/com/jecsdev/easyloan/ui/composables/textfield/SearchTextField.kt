package com.jecsdev.easyloan.ui.composables.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.ui.theme.ghostColor
import com.jecsdev.easyloan.ui.theme.navyBlueColor

@Composable
fun SearchTextField(
    searchText: String?,
    labelString: String,
    supportingTextLegend: String,
    modifier: Modifier
) {
    var searchValue by rememberSaveable {
        mutableStateOf(if (!searchText.isNullOrEmpty()) searchText else "")
    }
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp)
            .background(color = Color.Transparent),
        value = searchValue,
        colors = TextFieldDefaults.colors(
            cursorColor = navyBlueColor,
            disabledLabelColor = navyBlueColor,
            focusedIndicatorColor = navyBlueColor,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = ghostColor,
            unfocusedContainerColor = ghostColor
        ),
        onValueChange = { value -> searchValue = value },
        label = { Text(labelString) },
        supportingText = {
            Text(
                supportingTextLegend, color = colorResource(
                    id = R.color.dark_gray_color2
                )
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = stringResource(R.string.search_text_field_icon),
            )
        }
    )
}

@Composable
@Preview(showSystemUi = true)
fun SearchTextFieldPreview() {
    val demoString = stringResource(R.string.demo_string)
    SearchTextField(
        searchText = demoString, labelString = demoString,
        supportingTextLegend = demoString,
        modifier = Modifier
    )
}