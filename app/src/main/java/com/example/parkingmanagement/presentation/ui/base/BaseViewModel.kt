package com.example.parkingmanagement.presentation.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkingmanagement.common.Envelope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


open class BaseViewModel : ViewModel() {

    private val loadingLd = MutableLiveData<Boolean>()
    protected val errorLd = MutableLiveData<String>()

    val loading: LiveData<Boolean> = loadingLd
    val error: LiveData<String> = errorLd

    fun <T> Flow<Envelope<T>>.processResult(block: T?.() -> Unit) {
        viewModelScope.launch {
            this@processResult.collect { result ->
                when (result) {
                    is Envelope.Loading -> loadingLd.value = true
                    is Envelope.Failure -> {
                        errorLd.value = result.message
                        loadingLd.value = false
                    }
                    is Envelope.Success -> {
                        loadingLd.value = false
                        block.invoke(result.data)
                    }
                }
            }
        }
    }
}
