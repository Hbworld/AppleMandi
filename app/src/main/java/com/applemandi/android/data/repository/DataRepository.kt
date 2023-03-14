package com.applemandi.android.data.repository

import com.applemandi.android.data.local.DatabaseHelper
import com.applemandi.android.data.model.Seller
import com.applemandi.android.data.model.Village
import com.applemandi.android.data.remote.APIHelper
import kotlinx.coroutines.flow.Flow

interface DataRepository {

    fun getAllVillages(): Flow<List<Village>>

    fun getSellerByName(name: String): Seller?

    fun getSellerByLCId(id: String): Seller?

    class Impl constructor(
        private val apiHelper: APIHelper,
        private val databaseHelper: DatabaseHelper
    ) : DataRepository, DataAccessStrategy() {

        override fun getAllVillages(): Flow<List<Village>> {
            return performOperation(
                databaseQuery = { databaseHelper.getVillages() },
                networkCall = { apiHelper.getAllVillages() },
                saveResult = {
                    databaseHelper.updateVillages(it)
                })
        }

        override fun getSellerByName(name: String): Seller? {
            return apiHelper.getSellerByName(name)
        }

        override fun getSellerByLCId(id: String): Seller? {
            return apiHelper.getSellerByLCId(id)
        }

    }
}