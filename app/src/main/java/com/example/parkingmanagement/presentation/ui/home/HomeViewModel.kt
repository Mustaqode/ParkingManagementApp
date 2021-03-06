package com.example.parkingmanagement.presentation.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.parkingmanagement.domain.model.ParkingSpaceData
import com.example.parkingmanagement.domain.use_case.home.GetParkingSpaceDataUseCase
import com.example.parkingmanagement.presentation.ui.base.BaseViewModel


class HomeViewModel(
    private val getParkingSpaceDataUseCase: GetParkingSpaceDataUseCase
) : BaseViewModel() {

    private val parkingSpaceDataLd = MutableLiveData<ParkingSpaceData>()

    init {
        getParkingSpaceData()
    }

    val parkingSpaceData = parkingSpaceDataLd

    fun refresh() {
        getParkingSpaceData()
    }

    private fun getParkingSpaceData() {
        getParkingSpaceDataUseCase().processResult {
            parkingSpaceDataLd.value = this
        }
    }

}
