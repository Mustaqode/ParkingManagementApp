package com.example.parkingmanagement.data.repository

import com.example.parkingmanagement.data.db.AvailableParkingSpace
import com.example.parkingmanagement.data.db.ParkingManagementAppDatabase
import com.example.parkingmanagement.data.db.ParkingSpace
import com.example.parkingmanagement.data.mapper.ParkingSpaceAllotmentMapper
import com.example.parkingmanagement.domain.repository.NewParkingSpaceRepository


class NewParkingSpaceRepositoryImpl(private val database: ParkingManagementAppDatabase) :
    NewParkingSpaceRepository {

    override suspend fun addANewParkingSpace(parkingSpace: ParkingSpace) {
        database.addANewParkingSpace(ParkingSpaceAllotmentMapper.map(parkingSpace))
        for (i in 0..(parkingSpace.totalFloor ?: 0)) {
            database.addNewParkingSpace(
                AvailableParkingSpace(
                    i,
                    availableSpacesForCars = parkingSpace.noOfSpacesForCarEachFloor ?: 0,
                    availableSpacesForBikes = parkingSpace.noOfSpacesForBikeEachFloor ?: 0,
                    availableSpacesForBuses = parkingSpace.noOfSpacesForBusEachFloor ?: 0

                )
            )
        }
    }

}
