package com.applemandi.android.data.local

import com.applemandi.android.data.model.Village
import kotlinx.coroutines.flow.Flow

interface DatabaseHelper {

    suspend fun getVillages(): Flow<List<Village>>

    suspend fun updateVillages(villages: List<Village>)


    class Impl(private val appDatabase: AppDatabase) : DatabaseHelper {

        override suspend fun getVillages(): Flow<List<Village>> {
            return appDatabase.villageDao().getVillages()
        }

        override suspend fun updateVillages(villages: List<Village>) {
            appDatabase.villageDao().insertAll(villages)
        }
    }
}