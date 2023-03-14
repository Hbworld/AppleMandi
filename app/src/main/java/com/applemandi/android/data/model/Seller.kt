package com.applemandi.android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Seller(
    val name: String?,
    val isRegistered: Boolean = true,
    val loyaltyCardId: String?,
    val loyaltyIndex: Double = if (isRegistered) 1.12 else 0.98
) : Parcelable

