package com.jecsdev.easyloan.ui.screens.borrower

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.jecsdev.easyloan.feature_borrower.data.model.Borrower
import com.jecsdev.easyloan.ui.viewmodel.BorrowerViewModel

@Composable
fun BorrowerDetails(borrower: Borrower){

}

@Preview(showSystemUi = true)
@Composable
fun BorrowerDetailsPreview() {
    val borrower = Borrower()
    BorrowerDetails(borrower = borrower )
}