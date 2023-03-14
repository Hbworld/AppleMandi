package com.applemandi.android.data.repository

import com.applemandi.android.data.local.DatabaseHelper
import com.applemandi.android.data.model.Seller
import com.applemandi.android.data.model.Village
import com.applemandi.android.data.remote.APIHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

interface DataRepository {

    suspend fun getAllVillages(): Flow<List<Village>>

    fun getSellerByName(name: String): Seller?

    fun getSellerByLCId(id: String): Seller?

    class Impl constructor(
        private val apiHelper: APIHelper,
        private val databaseHelper: DatabaseHelper
    ) : DataRepository {

        override suspend fun getAllVillages(): Flow<List<Village>> {
            return databaseHelper.getVillages()
                .onEach { villages ->
                    if (villages.isEmpty()) {
                        fetchVillages()
                    }
                }
        }

        override fun getSellerByName(name: String): Seller? {
            return apiHelper.getSellerByName(name)
        }

        override fun getSellerByLCId(id: String): Seller? {
            return apiHelper.getSellerByLCId(id)
        }

        private suspend fun fetchVillages(): List<Village> {
            return apiHelper.getAllVillages()
                .also {
                    saveVillages(it)
                }
        }

        private suspend fun saveVillages(villages: List<Village>) {
            databaseHelper.updateVillages(villages)
        }

    }
}