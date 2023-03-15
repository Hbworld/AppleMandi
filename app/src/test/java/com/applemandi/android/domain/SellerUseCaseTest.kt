package com.applemandi.android.domain

import com.applemandi.android.data.model.Seller
import com.applemandi.android.data.repository.DataRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SellerUseCaseTest {

    private lateinit var sellerUseCase: SellerUseCase

    @Mock
    private lateinit var dataRepository: DataRepository

    private val registeredTestName = "Registered Test Name"
    private val registeredTestLCId = "Registered Test LC Id"
    private val unRegisteredTestName = "UnRegistered Test Name"
    private val unRegisteredTestLCId = "UnRegistered Test LC Id"

    @Before
    fun setup() {
        sellerUseCase = SellerUseCase.Impl(dataRepository)
    }

    @Test
    fun getSellerByName(): Unit = runTest {

        // testing for registered user
        Mockito.`when`(dataRepository.getSellerByName(registeredTestName))
            .thenReturn(
                Seller(
                    name = registeredTestName,
                    loyaltyCardId = registeredTestLCId,
                    isRegistered = true
                )
            )

        sellerUseCase.getSellerByName(registeredTestName).collect {
            assertEquals(
                it,
                Seller(
                    name = registeredTestName,
                    loyaltyCardId = registeredTestLCId,
                    isRegistered = true
                )
            )
        }

        // testing for unRegistered user
        Mockito.`when`(dataRepository.getSellerByName(unRegisteredTestName))
            .thenReturn(null)

        sellerUseCase.getSellerByName(unRegisteredTestName).collect {
            assertEquals(
                it,
                Seller(name = unRegisteredTestName, loyaltyCardId = null, isRegistered = false)
            )
        }
    }

    @Test
    fun getSellerByLCId(): Unit = runTest {

        // testing for registered loyalty card id
        Mockito.`when`(dataRepository.getSellerByLCId(registeredTestLCId))
            .thenReturn(
                Seller(
                    name = registeredTestName,
                    loyaltyCardId = registeredTestLCId,
                    isRegistered = true
                )
            )

        sellerUseCase.getSellerByLCId(registeredTestLCId).collect {
            assertEquals(
                it,
                Seller(
                    name = registeredTestName,
                    loyaltyCardId = registeredTestLCId,
                    isRegistered = true
                )
            )
        }

        // testing for unRegistered loyalty card id
        Mockito.`when`(dataRepository.getSellerByLCId(unRegisteredTestLCId))
            .thenReturn(null)

        sellerUseCase.getSellerByLCId(unRegisteredTestLCId).collect {
            assertEquals(
                it,
                Seller(name = null, loyaltyCardId = unRegisteredTestLCId, isRegistered = false)
            )
        }

    }
}