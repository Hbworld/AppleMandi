package com.applemandi.android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SellOrder(
    val grossPrice : Double,
    val sellerName : String,
    val grossWeight : Int
) : Parcelable
