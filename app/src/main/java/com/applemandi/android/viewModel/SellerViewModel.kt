package com.applemandi.android.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applemandi.android.data.repository.DataRepository
import com.applemandi.android.data.model.Seller
import com.applemandi.android.domain.SellerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellerViewModel @Inject constructor(private val sellerUseCase: SellerUseCase) :
    ViewModel() {

    private var debounceJob: Job? = null

    private val _seller = MutableStateFlow(Seller(name = null, loyaltyCardId = null))
    val seller: StateFlow<Seller> get() = _seller

    fun onSellerNameChange(char: CharSequence) {
        if (char.isNotEmpty()) debounce(400, viewModelScope, ::onSellerNameChanged, char)
    }

    fun onLoyaltyCardIdChange(char: CharSequence) {
        if (char.isNotEmpty()) debounce(500, viewModelScope, ::onLoyaltyCardIdChanged, char)
    }


    private fun onSellerNameChanged(char: CharSequence) {

        viewModelScope.launch {
            sellerUseCase.getSellerByName(char.toString()).collect {
                _seller.emit(it)
            }
        }

    }

    private fun onLoyaltyCardIdChanged(char: CharSequence) {
        viewModelScope.launch {
            sellerUseCase.getSellerByLCId(char.toString()).collect {
                _seller.emit(it)
            }
        }
    }

    private fun debounce(
        waitMs: Long = 500L,
        coroutineScope: CoroutineScope,
        func: (CharSequence) -> Unit,
        char: CharSequence
    ) {
        debounceJob?.cancel()
        debounceJob = coroutineScope.launch {
            delay(waitMs)
            func(char)
        }
    }
}