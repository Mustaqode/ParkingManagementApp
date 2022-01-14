package com.example.parkingmanagement.domain.model


data class ParkingSpaceData(
    val totalFloor: Int,
    val noOfParkingSpaceAllottedPerFloor: Int,
    val parkingSpaceAllotmentDetail: String,
    val totalNoOfEmptyParkingSpacesAvailableForCar: Int,
    val totalNoOfEmptyParkingSpacesAvailableForBike: Int,
    val totalNoOfEmptyParkingSpacesAvailableForBus: Int,
)
