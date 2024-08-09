package com.jecsdev.easyloan.feature_borrower.domain.use_case

import com.jecsdev.easyloan.feature_borrower.data.model.Borrower
import com.jecsdev.easyloan.feature_borrower.domain.repository.BorrowerRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetBorrowerTest {

    @MockK
    private lateinit var borrowerRepository: BorrowerRepository

    private lateinit var getBorrower: GetBorrower
    private var borrowerId: String = "borrowerId"

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getBorrower = GetBorrower(borrowerRepository)
    }

    @Test
    fun `should return empty borrower if borrower does not exist in repository`(): Unit = runBlocking {
        //Given
        val emptyBorrower = Borrower() // Borrower object with default values
        coEvery { borrowerRepository.getBorrower(null) } returns flowOf(emptyBorrower)

        //When
        val result = getBorrower(null).single()

        //Then
        assertEquals(emptyBorrower, result)
        coVerify(exactly = 1) { borrowerRepository.getBorrower(null) }
    }

    @Test
    fun `should return a borrower if exist in repository`(): Unit =  runBlocking {
        //Given
        val borrower = Borrower(id = borrowerId, name = "John Doe", identificationNumber = "0000000",
            address = "8005 West Street", phone = "8090000000", photo = "PhotoUri")
        coEvery { borrowerRepository.getBorrower(borrowerId) } returns flowOf(borrower)

        //When
        val result = getBorrower(borrowerId).single()

        //Then
        assertEquals(borrower, result)
        coVerify { borrowerRepository.getBorrower(borrowerId) }
    }


}