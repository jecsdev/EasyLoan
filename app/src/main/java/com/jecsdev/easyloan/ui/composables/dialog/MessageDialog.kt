package com.jecsdev.easyloan.ui.composables.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.jecsdev.easyloan.R

@Composable
fun MessageDialog(
    dialogTitle: String,
    dialogMessage: String,
    shouldShowConfirmButton: Boolean,
    shouldShowCancelButton: Boolean,
    onCancelButtonClicked: () -> Unit,
    onConfirmButtonClicked: () -> Unit
) {
    Dialog(onDismissRequest = onCancelButtonClicked) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White
        ) {
            Row() {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(text = dialogTitle, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = dialogMessage, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        if (shouldShowCancelButton) {
                            TextButton(onClick = onCancelButtonClicked) {
                                Text(stringResource(R.string.cancel))
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        if (shouldShowConfirmButton) {
                            TextButton(onClick = onConfirmButtonClicked) {
                                Text(stringResource(R.string.accept))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MessageDialogPreview() {
    MessageDialog(
        dialogTitle = "Demo dialog title",
        dialogMessage = "Demo dialog message ",
        shouldShowConfirmButton = true,
        shouldShowCancelButton = true,
        onCancelButtonClicked = {  }) {

    }
}