package com.example.parkingmanagement.domain.model


data class ParkingData(
    val vehicleNumber: String,
    val floorNumber: Int,
    val vehicleType: String,
    val timeOfParking: Long,
    val isFirstTime: Boolean,
    val offerCouponApplied:String,
)
