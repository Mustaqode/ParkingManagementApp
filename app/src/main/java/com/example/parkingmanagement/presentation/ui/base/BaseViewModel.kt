package com.example.parkingmanagement.presentation.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkingmanagement.common.Envelope
import com.example.parkingmanagement.presentation.helper.Trigger
import com.example.parkingmanagement.presentation.helper.extensions.trigger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


open class BaseViewModel : ViewModel() {

    protected val loadingLd = MutableLiveData<Trigger>()
    protected val errorLd = MutableLiveData<String>()

    val loading: LiveData<Trigger> = loadingLd
    val error: LiveData<String> = errorLd

    fun <T> Flow<Envelope<T>>.processResult(block: T?.() -> Unit) {
        viewModelScope.launch {
            this@processResult.collect { result ->
                when (result) {
                    is Envelope.Loading -> loadingLd.trigger()
                    is Envelope.Failure -> errorLd.value = result.message
                    is Envelope.Success -> block.invoke(result.data)
                }
            }
        }
    }
}
