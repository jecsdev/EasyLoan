package com.jecsdev.easyloan.ui.composables.swipetodismiss

import android.content.Context
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.ui.composables.dialog.MessageDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSwipeToDismissBox(
    context: Context,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    content: @Composable() () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val dismissState = rememberSwipeToDismissBoxState()
    val coroutineScope = rememberCoroutineScope()
    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.Settled -> Color.Transparent
                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                    SwipeToDismissBoxValue.StartToEnd -> Color.Transparent
                }, label = stringResource(id = R.string.empty_string)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        }
    ) {
        content()
    }

    if (showDialog) {
        MessageDialog(dialogTitle = context.getString(R.string.delete_borrower),
            dialogMessage = context.getString(R.string.delete_borrower_confirm_message),
            shouldShowConfirmButton = true,
            shouldShowCancelButton = true,
            onConfirmButtonClicked = {
                showDialog = false
                onDelete()
            },
            onCancelButtonClicked = {
                showDialog = false
                coroutineScope.launch {
                    dismissState.snapTo(SwipeToDismissBoxValue.Settled)
                }
            }
        )
    }


    when (dismissState.currentValue) {
        SwipeToDismissBoxValue.StartToEnd -> {

        }

        SwipeToDismissBoxValue.EndToStart -> {
            showDialog = true
        }

        SwipeToDismissBoxValue.Settled -> {

        }
    }
}