package com.applemandi.android.data.repository

import android.util.Log
import com.applemandi.android.data.model.Seller
import com.applemandi.android.data.model.Village
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

interface DataRepository {

    suspend fun getAllVillages(): Flow<List<Village>>

    fun getSellerByName(name: String): Seller?

    fun getSellerByLCId(id: String): Seller?

    class Impl constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource
    ) : DataRepository {

        override suspend fun getAllVillages(): Flow<List<Village>> {
            return localDataSource.getVillages()
                .onStart {
                    Log.d("getAllVillages", "it.toString()")

                }
                .onEach { villages ->
                    Log.d("getAllVillages", "it.toString()2")
                    if (villages.isEmpty()) {
                        Log.d("getAllVillages", "it.toString()3")
                        fetchVillages()
                    }
                }
        }

        override fun getSellerByName(name: String): Seller? {
            return remoteDataSource.getSellerByName(name)
        }

        override fun getSellerByLCId(id: String): Seller? {
            return remoteDataSource.getSellerByLCId(id)
        }

        private suspend fun fetchVillages(): List<Village> {
            return remoteDataSource.getAllVillages()
                .also {
                    saveVillages(it)
                }
        }

        private suspend fun saveVillages(villages: List<Village>) {
            localDataSource.updateVillages(villages)
        }

    }
}