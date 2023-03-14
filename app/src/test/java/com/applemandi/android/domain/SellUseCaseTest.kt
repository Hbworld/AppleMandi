package com.applemandi.android.domain


import com.applemandi.android.data.model.Village
import com.applemandi.android.data.repository.DataRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SellUseCaseTest {

    private lateinit var sellUseCase: SellUseCase

    @Mock
    private lateinit var dataRepository: DataRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sellUseCase = SellUseCase.Impl(dataRepository)

    }


    @Test
    fun getAllVillages(): Unit = runTest {

        Mockito.`when`(dataRepository.getAllVillages())
            .thenReturn(flow {
                emit(listOf(Village("TestVillage", 1.0)))
            })

        sellUseCase.getAllVillages().collect {
            assertEquals(
                listOf(Village("TestVillage", 1.0)),
                it
            )
        }


    }
}