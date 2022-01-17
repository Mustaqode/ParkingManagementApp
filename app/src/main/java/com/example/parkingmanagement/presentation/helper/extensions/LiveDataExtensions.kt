package com.example.parkingmanagement.presentation.helper.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.parkingmanagement.presentation.helper.Trigger


fun MutableLiveData<Trigger>.trigger() {
    this.value = Trigger
}

fun <T> LiveData<T>.observeLiveData(lifecycleOwner: LifecycleOwner, function: (T) -> Unit) {
    this.observe(lifecycleOwner, Observer {
        if (it != null)
            function.invoke(it)
    })
}
