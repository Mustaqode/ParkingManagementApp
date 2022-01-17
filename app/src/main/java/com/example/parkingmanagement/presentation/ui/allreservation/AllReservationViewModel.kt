package com.example.parkingmanagement.presentation.ui.allreservation

import androidx.lifecycle.MutableLiveData
import com.example.parkingmanagement.domain.model.ReservationData
import com.example.parkingmanagement.domain.use_case.allongoingreservation.GetAllOnGoingReservationUseCase
import com.example.parkingmanagement.domain.use_case.allongoingreservation.UnReserveUseCase
import com.example.parkingmanagement.presentation.helper.Trigger
import com.example.parkingmanagement.presentation.helper.extensions.trigger
import com.example.parkingmanagement.presentation.ui.base.BaseViewModel


class AllReservationViewModel(
    private val getAllOnGoingReservationUseCase: GetAllOnGoingReservationUseCase,
    private val unReserveUseCase: UnReserveUseCase
) : BaseViewModel() {


    private val allReservationLd = MutableLiveData<List<ReservationData>>()
    private val unReserveLd = MutableLiveData<Int>()
    private val noDataLd = MutableLiveData<Trigger>()

    init {
        getAllOnGoingReservationUseCase().processResult {
            if (this.isNullOrEmpty()) {
                noDataLd.trigger()
            } else
                allReservationLd.value = this
        }
    }

    val allReservation = allReservationLd
    val unReserve = unReserveLd
    val noData = noDataLd

    fun unReserve(parkingData: ReservationData, index: Int) {
        unReserveUseCase(parkingData).processResult {
            unReserveLd.value = index
        }
    }

}
