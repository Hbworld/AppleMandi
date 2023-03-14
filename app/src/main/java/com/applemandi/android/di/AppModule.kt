package com.applemandi.android.di

import android.content.Context
import com.applemandi.android.data.remote.APIService
import com.applemandi.android.data.local.AppDatabase
import com.applemandi.android.data.repository.DataRepository
import com.applemandi.android.domain.PriceUseCase
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
    fun provideDataRepository(apiService: APIService, appDatabase: AppDatabase) : DataRepository {
        return DataRepository.Impl(apiService, appDatabase)
    }

    @Singleton
    @Provides
    fun provideApiService() : APIService {
        return APIService.Impl()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) : AppDatabase {
        return AppDatabase.getDatabase(context)
    }
}