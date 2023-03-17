package com.applemandi.android.data.repository

import android.util.Log
import com.applemandi.android.data.local.databse.AppDatabase
import com.applemandi.android.data.model.Village
import com.applemandi.android.extensions.toDomain
import com.applemandi.android.extensions.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface LocalDataSource {

    suspend fun getVillages(): Flow<List<Village>>

    suspend fun updateVillages(villages: List<Village>)


    class Impl(private val appDatabase: AppDatabase) : LocalDataSource {

        override suspend fun getVillages(): Flow<List<Village>> {
            return appDatabase.villageDao().getVillages().map {
                it.map { villageEntity ->
                    villageEntity.toDomain()
                }
            }
        }

        override suspend fun updateVillages(villages: List<Village>) {
            appDatabase.villageDao().insertAll(villages.map { it.toEntity() })
        }
    }
}