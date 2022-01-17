package com.example.parkingmanagement.data.mapper

import com.example.parkingmanagement.data.db.OnGoingParking
import com.example.parkingmanagement.domain.model.ParkingData


object ParkingDataMapper {

    private const val OFFER_COUPON_APPLIED = "Offer Coupon Applied"
    private const val NONE = "None"

    fun map(onGoingParkingList: List<OnGoingParking>): List<ParkingData> {
        val parkingDataList = arrayListOf<ParkingData>()
        onGoingParkingList.forEach {
            parkingDataList.add(it.mapToParkingData())
        }
        return parkingDataList
    }

    private fun OnGoingParking.mapToParkingData() =
        ParkingData(
            vehicleNumber = this.vehicleNumber,
            vehicleType = this.vehicleType,
            floorNumber = this.floorNumber ?: 0,
            timeOfParking = this.timeOfParking,
            isFirstTime = this.isFirstTime ?: false,
            offerCouponApplied = if (isFirstTime == true) OFFER_COUPON_APPLIED else NONE
        )


}
