package com.jecsdev.easyloan.ui.composables.header

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jecsdev.easyloan.R


/**
 * This is the header text with arrow back icon to be reused in the app.
 * @param titleText the current title text in the screen.
 * @param navController navigation controller for each screen.
 */
@Composable
fun TitleHeader(titleText: String,navController: NavController?){
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { navController?.navigateUp()}) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back_icon_button)
            )
        }
        Text(
            modifier = Modifier.padding(),
            text = titleText,
            fontSize = 24.sp
        )
    }
}


