package com.example.parkingmanagement.data.repository

import com.example.parkingmanagement.data.db.AvailableParkingSpace
import com.example.parkingmanagement.data.db.ParkingManagementAppDatabase
import com.example.parkingmanagement.data.db.ParkingSpace
import com.example.parkingmanagement.data.mapper.ParkingSpaceAllotmentMapper
import com.example.parkingmanagement.domain.repository.NewParkingSpaceRepository


class NewParkingSpaceRepositoryImpl(private val database: ParkingManagementAppDatabase) :
    NewParkingSpaceRepository {

    override suspend fun addANewParkingSpace(parkingSpace: ParkingSpace) {
        val newParkingSpaceWithAllotment = ParkingSpaceAllotmentMapper.map(parkingSpace)
        database.addANewParkingSpace(newParkingSpaceWithAllotment)
        for (i in 1..(parkingSpace.totalFloor ?: 0)) {
            database.addNewParkingSpace(
                AvailableParkingSpace(
                    i,
                    availableSpacesForCars = newParkingSpaceWithAllotment.noOfSpacesForCarEachFloor ?: 0,
                    availableSpacesForBikes = newParkingSpaceWithAllotment.noOfSpacesForBikeEachFloor ?: 0,
                    availableSpacesForBuses = newParkingSpaceWithAllotment.noOfSpacesForBusEachFloor ?: 0

                )
            )
        }
    }

}
