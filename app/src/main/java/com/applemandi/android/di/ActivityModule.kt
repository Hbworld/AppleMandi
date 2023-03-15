package com.applemandi.android.di

import com.applemandi.android.domain.PriceUseCase
import com.applemandi.android.domain.VillageUseCase
import com.applemandi.android.domain.SellerUseCase
import com.applemandi.android.viewModel.SellViewModel
import com.applemandi.android.viewModel.SellerViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @ActivityScoped
    @Provides
    fun provideSellViewModel(villageUseCase: VillageUseCase, priceUseCase: PriceUseCase) : SellViewModel {
        return SellViewModel(villageUseCase, priceUseCase)
    }

    @ActivityScoped
    @Provides
    fun provideSellerViewModel(sellerUseCase: SellerUseCase) : SellerViewModel {
        return SellerViewModel(sellerUseCase)
    }



}