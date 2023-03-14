package com.applemandi.android.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseDataSource {

    fun <T> performOperation(
        databaseQuery: () -> Flow<T>,
        networkCall: suspend () -> T,
        saveResult: (T) -> Unit
    ): Flow<T> {

        return flow {
            val networkResponse = networkCall.invoke()
            saveResult(networkResponse)

            val source = databaseQuery.invoke()
            emitAll(source)

        }.flowOn(Dispatchers.IO)
    }

}