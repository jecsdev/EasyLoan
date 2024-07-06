package com.jecsdev.easyloan.ui.composables.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jecsdev.easyloan.R
import com.jecsdev.easyloan.feature_borrower.data.model.Borrower
import com.jecsdev.easyloan.ui.composables.shimmer.shimmer
import com.jecsdev.easyloan.ui.composables.text.CustomText
import com.jecsdev.easyloan.ui.theme.lightGrayColor

/**
 * Borrower's information card.
 */
@Composable
fun BorrowerCard(borrower: Borrower?, showShimmer: Boolean, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = lightGrayColor),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = borrower?.photo,
                    contentDescription = stringResource(R.string.profile_picture),
                    modifier = modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(shimmer(targetValue = 1300f, showShimmer = showShimmer)),
                    contentScale = ContentScale.Crop
                )

            }
            Spacer(modifier = modifier.width(8.dp))
            Column {
                CustomText(
                    text = borrower?.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .wrapContentWidth()
                        .background(
                            shimmer(targetValue = 1300f, showShimmer = showShimmer)
                        ),
                    showShimmer = showShimmer
                )
                CustomText(
                    text = borrower?.identificationNumber,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .wrapContentWidth()
                        .background(
                            shimmer(targetValue = 1300f, showShimmer = showShimmer)
                        ),
                    showShimmer = showShimmer
                )
                CustomText(
                    text = borrower?.address,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .wrapContentWidth()
                        .background(
                            shimmer(targetValue = 1300f, showShimmer = showShimmer)
                        ),
                    showShimmer = showShimmer
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun BorrowerCardPreview() {
    BorrowerCard(
        borrower = Borrower(
            "100",
            "John",
            "123456789",
            "0001-00052545-8",
            "Calle demo 123",
            ""
        ),
        showShimmer = true,
        modifier = Modifier
    )
}