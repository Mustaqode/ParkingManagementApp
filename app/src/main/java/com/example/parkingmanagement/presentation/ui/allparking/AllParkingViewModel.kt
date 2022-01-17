package com.example.parkingmanagement.presentation.ui.allparking

import androidx.lifecycle.MutableLiveData
import com.example.parkingmanagement.domain.model.ParkingData
import com.example.parkingmanagement.domain.use_case.allongoingparking.DepartUseCase
import com.example.parkingmanagement.domain.use_case.allongoingparking.GetAllOnGoingParkingUseCase
import com.example.parkingmanagement.presentation.helper.Trigger
import com.example.parkingmanagement.presentation.helper.extensions.trigger
import com.example.parkingmanagement.presentation.ui.base.BaseViewModel


class AllParkingViewModel(
    private val getAllOnGoingParkingUseCase: GetAllOnGoingParkingUseCase,
    private val departUseCase: DepartUseCase
) : BaseViewModel() {

    private val allParkingLd = MutableLiveData<List<ParkingData>>()
    private val departLd = MutableLiveData<Int>()
    private val noDataLd = MutableLiveData<Trigger>()

    init {
        getAllOnGoingParkingUseCase().processResult {
            if (this.isNullOrEmpty()) {
                noDataLd.trigger()
            } else
                allParkingLd.value = this
        }
    }

    val allParking = allParkingLd
    val depart = departLd
    val noData = noDataLd

    fun depart(parkingData: ParkingData, index: Int) {
        departUseCase(parkingData).processResult {
            departLd.value = index
        }
    }
}
