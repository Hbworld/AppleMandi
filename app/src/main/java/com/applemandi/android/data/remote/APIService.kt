package com.applemandi.android.data.remote

import com.applemandi.android.data.model.Seller
import com.applemandi.android.data.model.Village

interface APIService {

    fun getAllVillages(): List<Village>

    fun getSellerByName(name: String): Seller?

    fun getSellerByLCId(id: String): Seller?


    class Impl : APIService {

        override fun getAllVillages(): List<Village> {
            return listOf(
                Village("Solan", 100.23),
                Village("Kangra", 90.45),
                Village("Hamirpur", 125.50),
                Village("Una", 105.65),
                Village("Dhanotu", 130.75)
            )
        }

        override fun getSellerByName(name: String): Seller? {
             return getAllSellers().find { seller -> seller.name.equals(name, true) }
        }

        override fun getSellerByLCId(id: String): Seller? {
            return getAllSellers().find { seller -> seller.loyaltyCardId.equals(id, true) }
        }

        private fun getAllSellers(): List<Seller> {
            return listOf(
                Seller(name = "Vishal", loyaltyCardId = "LCI3245"),
                Seller(name = "Paschal", loyaltyCardId = "LCI3537"),
                Seller(name = "Nehal", loyaltyCardId = "LCI3618"),
                Seller(name = "Avichal", loyaltyCardId = "LCI4678"),
                Seller(name = "Raju", loyaltyCardId = "LCI4890"),
                Seller(name = "Sonu", loyaltyCardId = "LCI6921")
            )
        }

    }
}

