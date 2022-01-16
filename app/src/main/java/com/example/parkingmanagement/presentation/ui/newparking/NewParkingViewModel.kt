package com.example.parkingmanagement.presentation.ui.newparking

import androidx.lifecycle.MutableLiveData
import com.example.parkingmanagement.data.db.OnGoingParking
import com.example.parkingmanagement.domain.use_case.newparking.AddANewParkingUseCase
import com.example.parkingmanagement.domain.use_case.newparking.FetchCouponDetailUseCase
import com.example.parkingmanagement.presentation.helper.Trigger
import com.example.parkingmanagement.presentation.helper.extensions.trigger
import com.example.parkingmanagement.presentation.ui.base.BaseViewModel


class NewParkingViewModel(
    private val addANewParkingUseCase: AddANewParkingUseCase,
    private val fetchCouponDetailUseCase: FetchCouponDetailUseCase
) : BaseViewModel() {

    private val couponDetailsLd = MutableLiveData<String>()
    private val parkLd = MutableLiveData<Trigger>()

    init {
        fetchCouponDetailUseCase().processResult {
            couponDetailsLd.value = this
        }
    }

    val couponDetails = couponDetailsLd
    val park = parkLd

    fun park(vehicleNo: String, vehicleType: String) {
        if (vehicleNo.length < 5) {
            errorLd.value = ERROR_INVALID_VEHICLE_REG_NO
        } else {
            addANewParkingUseCase(
                OnGoingParking(
                    vehicleNumber = vehicleNo,
                    vehicleType = vehicleType
                )
            ).processResult {
                parkLd.trigger()
            }
        }
    }

    companion object {
        private const val ERROR_INVALID_VEHICLE_REG_NO =
            "A vehicle reg. no. should at least be of 5 characters in length!"
    }
}
