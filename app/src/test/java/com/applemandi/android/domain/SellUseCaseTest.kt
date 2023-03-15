package com.applemandi.android.domain


import app.cash.turbine.test
import com.applemandi.android.data.model.Village
import com.applemandi.android.data.repository.DataRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SellUseCaseTest {

    private lateinit var sellUseCase: SellUseCase

    @Mock
    private lateinit var dataRepository: DataRepository

    @Before
    fun setup() {
        sellUseCase = SellUseCase.Impl(dataRepository)

    }


    @Test
    fun getAllVillages(): Unit = runTest {

        doReturn(flowOf(listOf(Village("TestVillage", 1.0)))).`when`(dataRepository)
            .getAllVillages()

        sellUseCase.getAllVillages().test {
            assertEquals(listOf(Village("TestVillage", 1.0)), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        verify(dataRepository).getAllVillages()


    }
}