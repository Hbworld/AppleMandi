package com.applemandi.android.data.local.databse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.applemandi.android.data.local.dao.VillageDao
import com.applemandi.android.data.local.entity.VillageEntity

@Database(entities = [VillageEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun villageDao(): VillageDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "AppleMandi Database")
                .fallbackToDestructiveMigration()
                .build()
    }

}