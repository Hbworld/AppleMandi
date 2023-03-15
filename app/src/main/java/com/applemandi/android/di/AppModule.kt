package com.applemandi.android.di

import android.content.Context
import com.applemandi.android.data.remote.APIHelper
import com.applemandi.android.data.local.AppDatabase
import com.applemandi.android.data.local.DatabaseHelper
import com.applemandi.android.data.repository.DataRepository
import com.applemandi.android.domain.PriceUseCase
import com.applemandi.android.domain.VillageUseCase
import com.applemandi.android.domain.SellerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providePriceUseCase() : PriceUseCase{
        return PriceUseCase.Impl()
    }

    @Provides
    fun provideSellerUseCase(dataRepository: DataRepository) : SellerUseCase{
        return SellerUseCase.Impl(dataRepository)
    }

    @Provides
    fun provideSellUseCase(dataRepository: DataRepository) : VillageUseCase {
        return VillageUseCase.Impl(dataRepository)
    }


    @Provides
    fun provideDataRepository(apiHelper: APIHelper, databaseHelper: DatabaseHelper) : DataRepository {
        return DataRepository.Impl(apiHelper, databaseHelper)
    }

    @Provides
    fun provideDatabaseHelper(appDatabase: AppDatabase) : DatabaseHelper {
        return DatabaseHelper.Impl(appDatabase)
    }

    @Provides
    fun provideApiService() : APIHelper {
        return APIHelper.Impl()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) : AppDatabase {
        return AppDatabase.getDatabase(context)
    }
}