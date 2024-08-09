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


class GetBorrowersTest{

    @MockK
    private lateinit var borrowerRepository: BorrowerRepository

    lateinit var getBorrowers: GetBorrowers
    private var userId: String = "userId"
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getBorrowers = GetBorrowers(borrowerRepository)
    }

    @Test
    fun `should return a list of borrowers for a given user id`(): Unit = runBlocking {
        //Given
        val expectedBorrowers = listOf(
            Borrower(id = "1", name = "John Doe", identificationNumber = "0000000",
                address = "8005 West Street", phone = "8090000000", photo = "PhotoUri"),
            Borrower(id = "1", name = "Jane Doe", identificationNumber = "0000000",
                address = "8524 East Street", phone = "8090000000", photo = "PhotoUri")
        )
        coEvery { borrowerRepository.getBorrowers(userId) } returns flowOf(expectedBorrowers)

        //When
         val result = getBorrowers(userId).single()

        //then
        assertEquals(expectedBorrowers, result)
        coVerify (exactly = 1){ borrowerRepository.getBorrowers(userId) }
    }

    @Test
    fun `should return an emptyList if there is not a borrower list in repository`(): Unit = runBlocking {
        //Given
        val emptyBorrowerList = emptyList<Borrower>()
        coEvery { borrowerRepository.getBorrowers(userId) } returns flowOf(emptyBorrowerList)

        //when
        val result = getBorrowers(userId).single()

        //then
        assertEquals(emptyList<Borrower>(), result)
        coVerify(exactly = 1) { borrowerRepository.getBorrowers(userId) }
    }

    @Test
    fun `should return an empty flow list if there is not a borrower list in repository`(): Unit = runBlocking {
        //Given
        coEvery { borrowerRepository.getBorrowers(userId) } returns flowOf(emptyList())

        //when
        val result = getBorrowers(userId).single()

        //then
        assertEquals(emptyList<Borrower>(), result)
        coVerify(exactly = 1) { borrowerRepository.getBorrowers(userId) }
    }
}