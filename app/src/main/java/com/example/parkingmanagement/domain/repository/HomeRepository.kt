package com.example.parkingmanagement.domain.repository

import com.example.parkingmanagement.domain.model.ParkingSpaceData


interface HomeRepository {

    suspend fun getParkingSpaceData() : ParkingSpaceData

}
