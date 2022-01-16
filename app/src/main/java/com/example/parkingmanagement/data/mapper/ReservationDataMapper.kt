package com.example.parkingmanagement.data.mapper

import com.example.parkingmanagement.data.db.OnGoingReservation
import com.example.parkingmanagement.domain.model.ReservationData


object ReservationDataMapper {

    private const val OFFER_COUPON_APPLIED = "Offer Coupon Applied"
    private const val FULL_DAY_RESERVATION = "Offer Coupon Applied"
    private const val NONE = "None"

    fun map(onGoingReservationList: List<OnGoingReservation>): List<ReservationData> {
        val parkingDataList = arrayListOf<ReservationData>()
        onGoingReservationList.forEach {
            parkingDataList.add(it.mapToReservationData())
        }
        return parkingDataList
    }

    private fun OnGoingReservation.mapToReservationData() =
        ReservationData(
            vehicleNumber = this.vehicleNumber,
            vehicleType = this.vehicleType,
            floorNumber = this.floorNumber ?: 0,
            timeOfParking = this.timeOfParking,
            isFirstTime = this.isFirstTime ?: false,
            offerCouponApplied = if (isFirstTime == true) OFFER_COUPON_APPLIED else NONE,
            noOfHours = FULL_DAY_RESERVATION
        )

}
