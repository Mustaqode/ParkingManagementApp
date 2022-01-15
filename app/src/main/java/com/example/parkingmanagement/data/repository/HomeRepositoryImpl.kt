package com.example.parkingmanagement.data.repository

import com.example.parkingmanagement.data.db.ParkingManagementAppDatabase
import com.example.parkingmanagement.domain.model.ParkingSpaceData
import com.example.parkingmanagement.domain.repository.HomeRepository


class HomeRepositoryImpl(private val database: ParkingManagementAppDatabase) : HomeRepository {

    override suspend fun getParkingSpaceData(): ParkingSpaceData {
        val parkingSpaceDetail = database.getAllParkingSpace().first()
        val availableParkingSpaceData = database.getAllAvailableParkingSpaces()

        var totalSpacesAvailableForCars = 0
        var totalSpacesAvailableForBikes = 0
        var totalSpacesAvailableForBuses = 0

        availableParkingSpaceData.forEach {
            totalSpacesAvailableForCars += it.availableSpacesForCars
            totalSpacesAvailableForBikes += it.availableSpacesForBikes
            totalSpacesAvailableForBuses += it.availableSpacesForBuses
        }

        var allotmentDetail = ""
        with(parkingSpaceDetail) {
            allotmentDetail =
                "( $noOfSpacesForCarEachFloor Cars, $noOfSpacesForBikeEachFloor Bikes & $noOfSpacesForBusEachFloor Buses )"
        }

        return ParkingSpaceData(
            availableParkingSpaceData.size,
            parkingSpaceDetail.parkingSpaceEachFloor ?: 0,
            allotmentDetail,
            totalSpacesAvailableForCars,
            totalSpacesAvailableForBikes,
            totalSpacesAvailableForBuses
        )
    }
}
