package com.applemandi.android.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.applemandi.android.data.model.Village
import kotlinx.coroutines.flow.Flow

@Dao
interface VillageDao {

    @Query("SELECT * FROM villageTable")
    fun getAllFeed(): Flow<List<Village>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(villages: List<Village>)


}