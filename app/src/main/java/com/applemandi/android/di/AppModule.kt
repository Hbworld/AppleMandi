package com.applemandi.android.di

import android.content.Context
import com.applemandi.android.data.repository.RemoteDataSource
import com.applemandi.android.data.local.databse.AppDatabase
import com.applemandi.android.data.repository.LocalDataSource
import com.applemandi.android.data.repository.DataRepository
import com.applemandi.android.domain.DebounceUseCase
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
    fun provideDebounceUseCase() : DebounceUseCase{
        return DebounceUseCase.Impl()
    }

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
    fun provideDataRepository(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource) : DataRepository {
        return DataRepository.Impl(remoteDataSource, localDataSource)
    }

    @Provides
    fun provideDatabaseHelper(appDatabase: AppDatabase) : LocalDataSource {
        return LocalDataSource.Impl(appDatabase)
    }

    @Provides
    fun provideApiService() : RemoteDataSource {
        return RemoteDataSource.Impl()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) : AppDatabase {
        return AppDatabase.getDatabase(context)
    }
}