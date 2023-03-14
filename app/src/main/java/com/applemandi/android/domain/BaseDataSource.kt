package com.applemandi.android.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseDataSource {

    fun <T> performOperation(
        databaseQuery: () -> T,
        networkCall: suspend () -> T,
        saveResult: (T) -> Unit
    ): Flow<T> {

        return flow {
            val source = databaseQuery.invoke()
            emit(source)

            val networkResponse = networkCall.invoke()
            saveResult(networkResponse)

        }.flowOn(Dispatchers.IO)
    }

}