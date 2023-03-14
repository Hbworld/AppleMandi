package com.applemandi.android.di

import android.content.Context
import com.applemandi.android.data.remote.APIHelper
import com.applemandi.android.data.local.AppDatabase
import com.applemandi.android.data.local.DatabaseHelper
import com.applemandi.android.data.repository.DataRepository
import com.applemandi.android.domain.PriceUseCase
import com.applemandi.android.domain.SellUseCase
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

    @Singleton
    @Provides
    fun providePriceUseCase() : PriceUseCase{
        return PriceUseCase.Impl()
    }

    @Singleton
    @Provides
    fun provideSellerUseCase(dataRepository: DataRepository) : SellerUseCase{
        return SellerUseCase.Impl(dataRepository)
    }

    @Singleton
    @Provides
    fun provideSellUseCase(dataRepository: DataRepository) : SellUseCase {
        return SellUseCase.Impl(dataRepository)
    }


    @Singleton
    @Provides
    fun provideDataRepository(apiHelper: APIHelper, databaseHelper: DatabaseHelper) : DataRepository {
        return DataRepository.Impl(apiHelper, databaseHelper)
    }

    @Singleton
    @Provides
    fun provideDatabaseHelper(appDatabase: AppDatabase) : DatabaseHelper {
        return DatabaseHelper.Impl(appDatabase)
    }

    @Singleton
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