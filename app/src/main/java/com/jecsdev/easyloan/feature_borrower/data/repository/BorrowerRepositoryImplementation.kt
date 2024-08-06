package com.jecsdev.easyloan.feature_borrower.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.jecsdev.easyloan.feature_borrower.data.model.Borrower
import com.jecsdev.easyloan.feature_borrower.domain.repository.BorrowerRepository
import com.jecsdev.easyloan.utils.constants.ExceptionConstants
import com.jecsdev.easyloan.utils.constants.FirebaseCollectionNames.borrowers
import com.jecsdev.easyloan.utils.constants.FirebaseCollectionNames.id
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BorrowerRepositoryImplementation @Inject constructor(
    firestore: FirebaseFirestore
) : BorrowerRepository {
    private val borrowerCollection = firestore.collection(borrowers)
    override suspend fun getBorrowers(userId: String?): Flow<List<Borrower>> = flow {
        val snapshot = borrowerCollection.whereEqualTo("userId", userId).get().await()
        val borrowers = snapshot.documents.map { document ->
            document.toObject(Borrower::class.java)!!.copy(id = document.id)
        }
        emit(borrowers)
    }.catch { exception ->
        Log.e(ExceptionConstants.EXCEPTION_TAG, ExceptionConstants.EXCEPTION_MESSAGE, exception)
        emit(emptyList())
    }

    override suspend fun getBorrower(id: String): Flow<Borrower?> = flow {
        val document = borrowerCollection.document(id).get().await()
        val borrower = document.toObject<Borrower>()?.copy(id = document.id)
        emit(borrower)
    }.catch { exception ->
        Log.e(ExceptionConstants.EXCEPTION_TAG, ExceptionConstants.EXCEPTION_MESSAGE, exception)
        val emptyBorrower = Borrower("", "", "", "", "", "","")
        emit(emptyBorrower)
    }

    override suspend fun addBorrower(borrower: Borrower) {
        try {
            val documentReference = borrowerCollection.add(borrower).await()
            borrowerCollection.document(documentReference.id).update(id, documentReference.id)
                .await()
        } catch (exception: RuntimeException) {
            throw exception
        }
    }

    override suspend fun updateBorrower(borrower: Borrower) {
        try {
            borrower.id?.let { borrowerCollection.document(it).set(borrower).await() }
        } catch (exception: RuntimeException) {
            throw exception
        }
    }

    override suspend fun deleteBorrower(borrower: Borrower) {
        try {
            borrower.id?.let { borrowerCollection.document(it).delete().await() }
        } catch (exception: RuntimeException) {
            throw exception
        }
    }
}