package com.applemandi.android.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applemandi.android.data.model.Seller
import com.applemandi.android.domain.DebounceUseCase
import com.applemandi.android.domain.SellerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SellerViewModel @Inject constructor(
    private val sellerUseCase: SellerUseCase,
    private val debounceUseCase: DebounceUseCase
    ) :
    ViewModel() {

    private val _errorMessage = MutableSharedFlow<Unit>()
    val errorMessage: SharedFlow<Unit> get() = _errorMessage

    private val _seller = MutableStateFlow(Seller(name = null, loyaltyCardId = null))
    val seller: StateFlow<Seller> get() = _seller

    fun onSellerNameChange(char: CharSequence) {
        if (char.isNotEmpty()) debounceUseCase.debounce(
            coroutineScope = viewModelScope,
            func = ::onSellerNameChanged,
            char = char
        )
    }

    fun onLoyaltyCardIdChange(char: CharSequence) {
        if (char.isNotEmpty()) debounceUseCase.debounce(
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
}