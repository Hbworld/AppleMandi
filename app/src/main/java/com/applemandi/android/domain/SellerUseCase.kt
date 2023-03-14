package com.applemandi.android.domain

import com.applemandi.android.data.model.Seller
import com.applemandi.android.data.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface SellerUseCase {

    fun getSellerByName(name: String): Flow<Seller>

    fun getSellerByLCId(id: String): Flow<Seller>


    class Impl(private val dataRepository: DataRepository) : SellerUseCase {

        override fun getSellerByName(name: String): Flow<Seller> {
            return flow {
                dataRepository.getSellerByName(name).let {
                    if (it != null) emit(it) else emit(
                        Seller(
                            name = name,
                            isRegistered = false,
                            loyaltyCardId = null
                        )
                    )
                }
            }.flowOn(Dispatchers.IO)
        }

        override fun getSellerByLCId(id: String): Flow<Seller> {
            return flow {
                dataRepository.getSellerByLCId(id).let {
                    if (it != null) emit(it) else emit(
                        Seller(
                            name = null,
                            isRegistered = false,
                            loyaltyCardId = id
                        )
                    )
                }
            }.flowOn(Dispatchers.IO)
        }
    }

}