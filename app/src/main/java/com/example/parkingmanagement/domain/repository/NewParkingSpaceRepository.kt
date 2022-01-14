package com.example.parkingmanagement.domain.repository

import com.example.parkingmanagement.data.db.ParkingSpace


interface NewParkingSpaceRepository {

    suspend fun addANewParkingSpace(parkingSpace: ParkingSpace)

}
