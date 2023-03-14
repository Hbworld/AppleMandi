package com.applemandi.android.domain

interface PriceUseCase {

    fun calculateGrossPrice(
        rate: Double,
        loyaltyCardIndex: Double,
        grossWeight: Int
    ): Double


    class Impl : PriceUseCase {


        // rate is defined as per kg
        // final result is multiplied by a factor of 1000 as grossWeight is in tonnes
        override fun calculateGrossPrice(
            rate: Double,
            loyaltyCardIndex: Double,
            grossWeight: Int
        ): Double {
            return rate.times(loyaltyCardIndex).times(grossWeight).times(1000)
        }
    }

}