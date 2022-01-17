package com.example.parkingmanagement.presentation.ui.entrance

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.parkingmanagement.domain.use_case.entrance.GetExistingParkingSpaceUseCase
import com.example.parkingmanagement.domain.use_case.entrance.ResetAllDataUseCase
import com.example.parkingmanagement.presentation.helper.Trigger
import com.example.parkingmanagement.presentation.helper.extensions.trigger
import com.example.parkingmanagement.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.launch


class EntranceViewModel(
    private val getExistingParkingSpaceUseCase: GetExistingParkingSpaceUseCase,
    private val resetAllDataUseCase: ResetAllDataUseCase
) : BaseViewModel() {

    private val existingParkingSpaceLd = MutableLiveData<Trigger>()
    private val newParkingSpaceLd = MutableLiveData<Trigger>()
    private val resetAllDataLd = MutableLiveData<Trigger>()

    init {
        getExistingParkingSpace()
    }

    val existingParkingSpace = existingParkingSpaceLd
    val newParkingSpace = newParkingSpaceLd
    val resetAll = resetAllDataLd


    private fun getExistingParkingSpace() {
        getExistingParkingSpaceUseCase().processResult {
            if (this == null) newParkingSpaceLd.trigger()
            else existingParkingSpaceLd.trigger()
        }
    }

    fun resetAllData() {
        viewModelScope.launch {
            resetAllDataUseCase().processResult {
                resetAllDataLd.trigger()
            }
        }
    }
}
