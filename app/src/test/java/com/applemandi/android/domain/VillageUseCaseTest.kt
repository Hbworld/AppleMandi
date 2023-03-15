package com.applemandi.android.domain


import com.applemandi.android.data.model.Village
import com.applemandi.android.data.repository.DataRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class VillageUseCaseTest {

    private lateinit var villageUseCase: VillageUseCase

    @Mock
    private lateinit var dataRepository: DataRepository

    @Test
    fun getAllVillages(): Unit = runTest {

        villageUseCase = VillageUseCase.Impl(dataRepository)

        doReturn(flowOf(listOf(Village("TestVillage", 1.0)))).`when`(dataRepository)
            .getAllVillages()

        villageUseCase.getAllVillages().collect {
            assertEquals(
                it,
                listOf(Village("TestVillage", 1.0))
            )
        }


    }
}