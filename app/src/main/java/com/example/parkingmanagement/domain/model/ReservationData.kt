package com.example.parkingmanagement.domain.model


data class ReservationData(
    val vehicleNumber: String,
    val floorNumber: Int,
    val vehicleType: String,
    val timeOfParking: Long,
    val isFirstTime: Boolean,
    val offerCouponApplied:String,
    val noOfHours: String
)
