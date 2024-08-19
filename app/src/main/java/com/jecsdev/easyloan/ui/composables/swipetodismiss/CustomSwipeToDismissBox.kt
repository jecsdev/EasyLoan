package com.jecsdev.easyloan.ui.composables.swipetodismiss

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.ui.composables.dialog.MessageDialog
import kotlinx.coroutines.delay

import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSwipeToDismissBox(
    dialogTitle: String,
    dialogMessage: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    animationDuration: Int = 500,
    content: @Composable () -> Unit
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val dismissState = rememberSwipeToDismissBoxState()
    val coroutineScope = rememberCoroutineScope()
    var isRemoved by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            showDialog = false
            delay(animationDuration.toLong())
        }
    }
    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = dismissState,
            backgroundContent = {
                val color by animateColorAsState(
                    targetValue = when (dismissState.targetValue) {
                        SwipeToDismissBoxValue.Settled -> Color.Transparent
                        SwipeToDismissBoxValue.EndToStart -> Color.Red
                        SwipeToDismissBoxValue.StartToEnd -> Color.Blue
                    }, label = stringResource(id = R.string.empty_string)
                )
                if (color == Color.Red) {
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
                } else if (color == Color.Blue) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White
                        )
                    }
                }
            }
        ) {
            content()
        }
    }

    if (showDialog) {
        MessageDialog(dialogTitle = dialogTitle,
            dialogMessage = dialogMessage,
            shouldShowConfirmButton = true,
            shouldShowCancelButton = true,
            onConfirmButtonClicked = {
                isRemoved = true
                onDelete()
            },
            onCancelButtonClicked = {
                showDialog = false
                coroutineScope.launch {
                    dismissState.reset()
                }
            }
        )
    }

    LaunchedEffect(dismissState.currentValue) {
        when (dismissState.currentValue) {
            SwipeToDismissBoxValue.Settled -> {

            }

            SwipeToDismissBoxValue.StartToEnd -> {

            }

            SwipeToDismissBoxValue.EndToStart -> {
                showDialog = true
            }
        }
    }
}


