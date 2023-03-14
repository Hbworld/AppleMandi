package com.applemandi.android.data.repository

import android.util.Log
import com.applemandi.android.data.remote.APIService
import com.applemandi.android.data.local.AppDatabase
import com.applemandi.android.data.model.Seller
import com.applemandi.android.data.model.Village
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface DataRepository {

    fun getAllVillages(): Flow<List<Village>>

    fun getSellerByName(name: String): Seller?

    fun getSellerByLCId(id: String): Seller?

    class Impl constructor(
        private val apiService: APIService,
        private val appDatabase: AppDatabase
    ) : DataRepository, BaseDataSource() {

        override fun getAllVillages(): Flow<List<Village>> {

            return performOperation(
                databaseQuery = { appDatabase.villageDao().getAllFeed() },
                networkCall = { apiService.getAllVillages() },
                saveResult = {
                    Log.d("performOperation", it.toString())
                    appDatabase.villageDao().insertAll(it)
                })

        }

        override fun getSellerByName(name: String): Seller? {
            return apiService.getSellerByName(name)
        }

        override fun getSellerByLCId(id: String): Seller? {
            return apiService.getSellerByLCId(id)
        }

    }
}