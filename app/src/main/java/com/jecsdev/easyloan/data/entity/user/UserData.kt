package com.jecsdev.easyloan.data.entity.user

/**
 * This class represents the data from the user.
 * @param userId user identifier.
 * @param userName user name from account.
 * @param profilePictureUri  uri to show profile picture.
 */
data class UserData(
    val userId: String,
    val userName: String,
    val profilePictureUri: String?
)
