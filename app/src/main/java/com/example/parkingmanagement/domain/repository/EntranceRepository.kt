package com.example.parkingmanagement.domain.repository

import com.example.parkingmanagement.data.db.ParkingSpace


interface EntranceRepository {

    suspend fun getParkingSpaceData() : ParkingSpace?

    suspend fun deleteAllData()

}
