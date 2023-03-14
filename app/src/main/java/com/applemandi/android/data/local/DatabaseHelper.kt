package com.applemandi.android.data.local

import com.applemandi.android.data.model.Village

interface DatabaseHelper {

    fun getVillages(): List<Village>

    fun updateVillages(villages: List<Village>)


    class Impl(private val appDatabase: AppDatabase) : DatabaseHelper {

        override fun getVillages(): List<Village> {
            return appDatabase.villageDao().getVillages()
        }

        override fun updateVillages(villages: List<Village>) {
            appDatabase.villageDao().insertAll(villages)
        }
    }
}