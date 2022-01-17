package com.example.parkingmanagement.presentation.ui.newreservation

import androidx.lifecycle.MutableLiveData
import com.example.parkingmanagement.data.db.OnGoingReservation
import com.example.parkingmanagement.domain.use_case.newparking.FetchCouponDetailUseCase
import com.example.parkingmanagement.domain.use_case.newreservation.MakeANewReservationUseCase
import com.example.parkingmanagement.presentation.helper.Trigger
import com.example.parkingmanagement.presentation.helper.extensions.trigger
import com.example.parkingmanagement.presentation.ui.base.BaseViewModel


class NewReservationViewModel(
    private val makeANewReservationUseCase: MakeANewReservationUseCase,
    private val fetchCouponDetailUseCase: FetchCouponDetailUseCase
) : BaseViewModel() {

    private val couponDetailsLd = MutableLiveData<String>()
    private val reserveLd = MutableLiveData<Trigger>()

    init {
        fetchCouponDetailUseCase().processResult {
            couponDetailsLd.value = this
        }
    }

    val couponDetails = couponDetailsLd
    val reserve = reserveLd

    fun reserve(vehicleNo: String, vehicleType: String) {
        if (vehicleNo.length < 5) {
            errorLd.value = ERROR_INVALID_VEHICLE_REG_NO
        } else {
            makeANewReservationUseCase(
                OnGoingReservation(
                    vehicleNumber = vehicleNo,
                    vehicleType = vehicleType
                )
            ).processResult {
                reserveLd.trigger()
            }
        }
    }

    companion object {
        private const val ERROR_INVALID_VEHICLE_REG_NO =
            "A vehicle reg. no. should at least be of 5 characters in length!"
    }
}
