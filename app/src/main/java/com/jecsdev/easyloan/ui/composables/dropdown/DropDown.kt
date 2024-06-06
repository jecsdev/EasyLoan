package com.jecsdev.easyloan.ui.composables.dropdown

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.ui.theme.ghostColor
import com.jecsdev.easyloan.ui.theme.navyBlueColor

@Composable
fun DropDown(
    label: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val icon = if (isExpanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { newQuery ->
                searchQuery = newQuery
                isExpanded = true
            },
            label = { Text(text = label) },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                cursorColor = navyBlueColor,
                disabledLabelColor = navyBlueColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = ghostColor,
                unfocusedContainerColor = ghostColor
            ),
            maxLines = 1,
            trailingIcon = {
                Icon(
                    icon,
                    contentDescription = stringResource(R.string.arrow_state_icon),
                    modifier = Modifier.clickable { isExpanded = !isExpanded })
            }
        )
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            val filteredOptions = options.filter { it.contains(searchQuery, ignoreCase = true) }
            filteredOptions.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        selectedOption = option
                        onOptionSelected(option)
                        searchQuery = option
                        isExpanded = false
                    },
                    text = {
                        Text(
                            text = option,
                            fontSize = 12.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DropDownMenuPreview() {
    val demoOptions = listOf("Semanal", "Quincenal", "Mensual")
    DropDown(
        label = "Label ",
        options = demoOptions,
        onOptionSelected = { _ -> }
    )
}