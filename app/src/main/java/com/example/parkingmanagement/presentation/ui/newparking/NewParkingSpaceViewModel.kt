package com.example.parkingmanagement.presentation.ui.newparking

import androidx.lifecycle.MutableLiveData
import com.example.parkingmanagement.data.db.ParkingSpace
import com.example.parkingmanagement.domain.use_case.newparking.FetchCouponDetailUseCase
import com.example.parkingmanagement.domain.use_case.newparkingspace.AddANewParkingSpaceUseCase
import com.example.parkingmanagement.presentation.helper.Trigger
import com.example.parkingmanagement.presentation.helper.extensions.trigger
import com.example.parkingmanagement.presentation.ui.base.BaseViewModel


class NewParkingSpaceViewModel(
    private val addANewParkingSpaceUseCase: AddANewParkingSpaceUseCase,
    private val couponDetailUseCase: FetchCouponDetailUseCase
) : BaseViewModel() {

    private val parkingSpaceCreationLd = MutableLiveData<Trigger>()
    private val couponDetailsLd = MutableLiveData<String>()

    init {
        /**
         * Move it
         */
        couponDetailUseCase().processResult {
            couponDetailsLd.value = this!!
        }
    }

    val parkingSpaceCreation = parkingSpaceCreationLd
    val couponDetails = couponDetailsLd

    fun createSpace(noOfFloors: Int?, noOfSpaceEachFloor: Int?) {
        when {
            noOfFloors == null || noOfFloors == 0 -> errorLd.value = ERROR_NO_OF_FLOOR
            noOfSpaceEachFloor == null || noOfSpaceEachFloor < 3 -> errorLd.value = ERROR_NO_OF_SPACES
            else -> {
                addANewParkingSpaceUseCase(
                    ParkingSpace(
                        totalFloor = noOfFloors,
                        parkingSpaceEachFloor = noOfSpaceEachFloor
                    )
                ).processResult {
                    parkingSpaceCreationLd.trigger()
                }
            }
        }
    }

    companion object {
        private const val ERROR_NO_OF_FLOOR = "No. of floors must at least be 1"
        private const val ERROR_NO_OF_SPACES = "No. of spaces should at least be 3"
    }

}
