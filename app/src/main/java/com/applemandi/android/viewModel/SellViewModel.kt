package com.applemandi.android.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applemandi.android.R
import com.applemandi.android.data.model.Seller
import com.applemandi.android.data.model.Village
import com.applemandi.android.domain.PriceUseCase
import com.applemandi.android.domain.SellUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SellViewModel @Inject constructor(
    private val sellUseCase: SellUseCase,
    private val priceUseCase: PriceUseCase
) : ViewModel() {

    private val _errorMessage = MutableSharedFlow<Unit>()
    val errorMessage : SharedFlow<Unit> get() = _errorMessage

    private val _villages = MutableStateFlow<List<Village>>(listOf())
    val villages: StateFlow<List<Village>> get() = _villages

    lateinit var seller: Seller

    private val _villageRate = MutableStateFlow(0.0)
    private val villageRate: StateFlow<Double> get() = _villageRate

    private val _grossWeight = MutableStateFlow(0)
    val grossWeight: StateFlow<Int> get() = _grossWeight

    private val _grossPrice = MutableStateFlow(0.0)
    val grossPrice: StateFlow<Double> get() = _grossPrice

    private var debounceJob: Job? = null


    fun setSellerData(seller: Seller) {
        this.seller = seller
    }

     fun loadVillages() {
        viewModelScope.launch(Dispatchers.IO) {
            sellUseCase.getAllVillages()
                .catch {
                    Log.d("loadVillages", "error -> "+it.message)
                    _errorMessage.emit(Unit)
                }
                .collect {
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
        debounce(coroutineScope = viewModelScope, func = ::onGrossWeightChanged, char = char)
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

    private fun updateGrossPrice() {
        viewModelScope.launch {
            val price = priceUseCase.calculateGrossPrice(
                rate = villageRate.value,
                grossWeight = grossWeight.value,
                loyaltyCardIndex = seller.loyaltyIndex
            )

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