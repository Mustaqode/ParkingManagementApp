package com.example.parkingmanagement.data.repository

import com.example.parkingmanagement.data.db.ParkingManagementAppDatabase
import com.example.parkingmanagement.data.db.ParkingSpace
import com.example.parkingmanagement.domain.repository.EntranceRepository


class EntranceRepositoryImpl(private val database: ParkingManagementAppDatabase) : EntranceRepository {

    override suspend fun getParkingSpaceData(): ParkingSpace? {
        val parkingSpaces = database.getAllParkingSpace()
        return if(parkingSpaces.isEmpty()) null
        else parkingSpaces.first()
    }


    override suspend fun deleteAllData() {
        database.run {
            deleteAllAvailableParkingSpaces()
            deleteAllFromTheRegistry()
            deleteAllOnGoingParking()
            deleteAllParkingSpaces()
            deleteAllTransactionSummary()
            deleteAllOnGoingReservation()
        }
    }
}
