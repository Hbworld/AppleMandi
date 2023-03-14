package com.applemandi.android.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class PriceUseCaseTest {

    private val priceUseCase = PriceUseCase.Impl()


    @Test
    fun calculateGrossPrice() {

        val expectedGrossPrice = 1000.00

        val finalGrossPrice = priceUseCase.calculateGrossPrice(
            rate = 1.00,
            loyaltyCardIndex = 1.00,
            1
        )

        assertEquals(
            expectedGrossPrice,
            finalGrossPrice,
            0.001 // This is for number precision
        )

    }
}