package com.applemandi.android.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applemandi.android.data.model.Seller
import com.applemandi.android.data.model.Village
import com.applemandi.android.data.repository.DataRepository
import com.applemandi.android.domain.PriceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val priceUseCase: PriceUseCase
) : ViewModel() {

    private val _villages = MutableStateFlow<List<Village>>(listOf())
    val villages: StateFlow<List<Village>> get() = _villages

    lateinit var seller: Seller

    private val _villageRate = MutableStateFlow(0.0)
    val villageRate: StateFlow<Double> get() = _villageRate

    private val _grossWeight = MutableStateFlow(0)
    val grossWeight: StateFlow<Int> get() = _grossWeight

    private val _grossPrice = MutableStateFlow(0.0)
    val grossPrice: StateFlow<Double> get() = _grossPrice

    private var debounceJob: Job? = null


    init {
        loadVillages()
    }

    fun setSellerData(seller: Seller){
        this.seller = seller
    }

    private fun loadVillages() {

        viewModelScope.launch {
            dataRepository.getAllVillages().collect {
                Log.d("loadVillages", it.toString())
                _villages.emit(it)

            }
        }
    }


    fun updateVillageRate(rate: Double) {
        viewModelScope.launch {
            _villageRate.emit(rate)
            updateGrossPrice()
        }
    }

    fun onGrossWeightChange(char: CharSequence?) {
        debounce(800, viewModelScope, ::onGrossWeightChanged, char)

    }

    private fun onGrossWeightChanged(char: CharSequence?) {
        try {
            updateGrossWeight(grossWeight = Integer.valueOf(char.toString()))
        } catch (e: NumberFormatException) {
            updateGrossWeight(grossWeight = 0)
        }
    }

    private fun updateGrossWeight(grossWeight: Int) {
        viewModelScope.launch {
            _grossWeight.emit(grossWeight)
            updateGrossPrice()
        }
    }

    fun updateGrossPrice() {
        viewModelScope.launch {
            val price = priceUseCase.calculateGrossPrice(rate = villageRate.value,
            grossWeight = grossWeight.value,
            loyaltyCardIndex = seller.loyaltyIndex)

            _grossPrice.emit(price)
        }
    }

    private fun debounce(
        waitMs: Long = 500L,
        coroutineScope: CoroutineScope,
        func: (CharSequence?) -> Unit,
        char: CharSequence?
    ) {
        debounceJob?.cancel()
        debounceJob = coroutineScope.launch {
            delay(waitMs)
            func(char)
        }
    }


}