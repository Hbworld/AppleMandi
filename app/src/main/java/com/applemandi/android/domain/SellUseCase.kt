package com.applemandi.android.domain

import com.applemandi.android.data.model.Village
import com.applemandi.android.data.repository.DataRepository
import kotlinx.coroutines.flow.Flow

interface SellUseCase {

    suspend fun getAllVillages(): Flow<List<Village>>


    class Impl(private val dataRepository: DataRepository) : SellUseCase{

        override suspend fun getAllVillages(): Flow<List<Village>> {
            return dataRepository.getAllVillages()
        }
    }


}