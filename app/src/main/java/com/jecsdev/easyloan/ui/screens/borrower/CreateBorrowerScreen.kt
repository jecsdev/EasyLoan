package com.jecsdev.easyloan.ui.screens.borrower

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.feature_borrower.data.model.Borrower
import com.jecsdev.easyloan.presentation.uihelpers.InputType
import com.jecsdev.easyloan.ui.composables.dialog.InfoDialog
import com.jecsdev.easyloan.ui.composables.dialog.MessageDialog
import com.jecsdev.easyloan.ui.composables.header.TitleHeader
import com.jecsdev.easyloan.ui.composables.textfield.SimpleTextField
import com.jecsdev.easyloan.ui.state.BorrowerState
import com.jecsdev.easyloan.ui.theme.navyBlueColor
import com.jecsdev.easyloan.ui.viewmodel.BorrowerViewModel
import com.jecsdev.easyloan.utils.constants.ExceptionConstants.EXCEPTION_TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

/**
 * This is the borrower creation screen.
 */
@Composable
fun CreateBorrowerScreen(viewModel: BorrowerViewModel, navController: NavController?) {
    val coroutineScope = rememberCoroutineScope()
    val userId = viewModel.getSignedUserId() ?: ""
    CreateBorrowerScreenContent(
        userId = userId,
        scope = coroutineScope,
        onAddBorrower = { borrower ->
            coroutineScope.launch(Dispatchers.IO) {
                viewModel.addBorrower(borrower = borrower)
            }
        },
        navController = navController
    )
}

@Composable
fun CreateBorrowerScreenContent(
    userId: String,
    scope: CoroutineScope,
    onAddBorrower: (Borrower) -> Unit,
    navController: NavController?
) {
    var name by rememberSaveable {
        mutableStateOf("")
    }
    var identificationNumber by rememberSaveable {
        mutableStateOf("")
    }
    var phoneNumber by rememberSaveable {
        mutableStateOf("")
    }
    var address by rememberSaveable {
        mutableStateOf("")
    }
    var photoUri by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }
    val photoPicketLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> photoUri = uri })
    var showErrorDialog by remember { mutableStateOf(false) }
    var showInfoDialog by remember { mutableStateOf(false) }
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                if (validateInputs(name, identificationNumber, address)) {
                    showInfoDialog = true
                    scope.launch(Dispatchers.IO) {
                        saveBorrower(
                            scope = scope,
                            onAddBorrower = onAddBorrower,
                            photoUri = photoUri,
                            borrowerState = BorrowerState.ReadyToSave(
                                Borrower(
                                    userId = userId,
                                    name = name,
                                    identificationNumber = identificationNumber,
                                    phone = phoneNumber,
                                    address = address,
                                    photo = photoUri.toString()
                                )
                            ),
                            navController = navController
                        )
                    }
                } else {
                    showErrorDialog = true
                }
            },
            containerColor = navyBlueColor,
            contentColor = Color.White
        ) {
            Icon(
                Icons.Filled.Save,
                stringResource(R.string.floating_action_button_add_borrower_description)
            )
        }
    }, containerColor = colorResource(id = R.color.phantom_gray_color)) { paddingValues ->
        paddingValues.calculateBottomPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp)
        ) {
            TitleHeader(
                titleText = stringResource(id = R.string.add_borrower),
                navController = navController
            )
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(model = photoUri ?: R.drawable.borrower_icon,
                    contentDescription = stringResource(
                        R.string.selected_image_description
                    ),
                    modifier = Modifier
                        .height(120.dp)
                        .width(120.dp)
                        .clip(CircleShape)
                        .clickable {
                            photoPicketLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        })
                Spacer(modifier = Modifier.height(12.dp))
                SimpleTextField(
                    textTyped = name,
                    labelValue = stringResource(id = R.string.name),
                    onValueChange = { value -> name = value },
                    isSingleLine = true,
                    inputType = InputType.TEXT,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(12.dp))
                SimpleTextField(
                    textTyped = identificationNumber,
                    labelValue = stringResource(id = R.string.identification_number),
                    onValueChange = { value -> identificationNumber = value },
                    isSingleLine = true,
                    inputType = InputType.NUMBER,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(12.dp))
                SimpleTextField(
                    textTyped = phoneNumber,
                    labelValue = stringResource(id = R.string.phone_number),
                    onValueChange = { value -> phoneNumber = value },
                    isSingleLine = true,
                    inputType = InputType.PHONE,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(12.dp))
                SimpleTextField(
                    textTyped = address,
                    labelValue = stringResource(id = R.string.address),
                    onValueChange = { value -> address = value },
                    isSingleLine = true,
                    inputType = InputType.TEXT,
                    modifier = Modifier
                )
            }
        }
    }
    if (showErrorDialog) {
        MessageDialog(
            dialogTitle = stringResource(R.string.error_saving_borrower),
            dialogMessage = stringResource(R.string.all_field_must_be_fulfilled),
            shouldShowConfirmButton = true,
            shouldShowCancelButton = false,
            onCancelButtonClicked = { showErrorDialog = false },
            onConfirmButtonClicked = { showErrorDialog = false }
        )
    }

    if (showInfoDialog) {
        InfoDialog(
            dialogTitle = stringResource(R.string.processing),
            dialogMessage = stringResource(R.string.saving_borrower_info),
            onDismissRequest = { showInfoDialog = false }
        )

    }
}

/**
 * Create borrower Screen preview.
 */
@Composable
@Preview(showSystemUi = true)
fun CreateBorrowerScreenPreview() {
    val coroutineScope = rememberCoroutineScope()
    CreateBorrowerScreenContent(
        userId = "",
        scope = coroutineScope,
        onAddBorrower = {},
        navController = null
    )
}

/**
 * Stores the current borrower in database.
 */
suspend fun saveBorrower(
    scope: CoroutineScope,
    onAddBorrower: (Borrower) -> Unit,
    photoUri: Uri?,
    borrowerState: BorrowerState.ReadyToSave,
    navController: NavController?
) {

    scope.launch {
        try {
            val photoUrl = photoUri?.let { uploadPhotoToStorage(it) }
            val borrower = borrowerState.borrower.copy(photo = photoUrl.toString())

            onAddBorrower(borrower)
            navController?.navigateUp()
        } catch (exception: Exception) {
            Log.e(EXCEPTION_TAG, exception.message.toString())
        }
    }

}

suspend fun uploadPhotoToStorage(photoUri: Uri): String? {
    return try {
        val storageRef: StorageReference = FirebaseStorage.getInstance().reference
        val photoRef = storageRef.child("borrowers/${UUID.randomUUID()}")
        photoRef.putFile(photoUri).await()
        photoRef.downloadUrl.await().toString()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun validateInputs(name: String, identificationNumber: String, address: String): Boolean {
    return name.isNotEmpty() && identificationNumber.isNotEmpty() && address.isNotEmpty()
}
