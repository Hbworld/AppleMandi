package com.applemandi.android.di

import com.applemandi.android.domain.DebounceUseCase
import com.applemandi.android.domain.PriceUseCase
import com.applemandi.android.domain.SellerUseCase
import com.applemandi.android.domain.VillageUseCase
import com.applemandi.android.viewModel.SellViewModel
import com.applemandi.android.viewModel.SellerViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @ViewModelScoped
    @Provides
    fun provideSellViewModel(
        villageUseCase: VillageUseCase,
        priceUseCase: PriceUseCase,
        debounceUseCase: DebounceUseCase
    ): SellViewModel {
        return SellViewModel(villageUseCase, priceUseCase, debounceUseCase)
    }

    @ViewModelScoped
    @Provides
    fun provideSellerViewModel(sellerUseCase: SellerUseCase, debounceUseCase: DebounceUseCase): SellerViewModel {
        return SellerViewModel(sellerUseCase, debounceUseCase)
    }


}