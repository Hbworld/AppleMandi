package com.applemandi.android.data.model


data class Village(
    val name: String,
    val rate: Double // per Kg INR
) {
    override fun toString(): String {
        return name
    }
}