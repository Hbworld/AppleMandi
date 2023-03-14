package com.applemandi.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "villageTable")
data class Village(
    @PrimaryKey
    val name: String,
    val rate: Double // per Kg INR
) {

    override fun toString(): String {
        return name
    }
}