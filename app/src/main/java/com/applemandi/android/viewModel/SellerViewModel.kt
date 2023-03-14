package com.applemandi.android.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applemandi.android.data.model.Seller
import com.applemandi.android.domain.SellerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellerViewModel @Inject constructor(private val sellerUseCase: SellerUseCase) :
    ViewModel() {

    private val _errorMessage = MutableSharedFlow<Unit>()
    val errorMessage: SharedFlow<Unit> get() = _errorMessage

    private var debounceJob: Job? = null

    private val _seller = MutableStateFlow(Seller(name = null, loyaltyCardId = null))
    val seller: StateFlow<Seller> get() = _seller

    fun onSellerNameChange(char: CharSequence) {
        if (char.isNotEmpty()) debounce(
            coroutineScope = viewModelScope,
            func = ::onSellerNameChanged,
            char = char
        )
    }

    fun onLoyaltyCardIdChange(char: CharSequence) {
        if (char.isNotEmpty()) debounce(
            coroutineScope = viewModelScope,
            func = ::onLoyaltyCardIdChanged,
            char = char
        )
    }

    private fun onSellerNameChanged(char: CharSequence) {
        viewModelScope.launch {
            sellerUseCase.getSellerByName(char.toString())
                .catch {
                    _errorMessage.emit(Unit)
                }.collect {
                    _seller.emit(it)
                }
        }
    }

    private fun onLoyaltyCardIdChanged(char: CharSequence) {
        viewModelScope.launch {
            sellerUseCase.getSellerByLCId(char.toString())
                .catch {
                    _errorMessage.emit(Unit)
                }.collect {
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

    override fun onCleared() {
        super.onCleared()
        Log.d("SellerViewModel", "onCleared")
    }
}