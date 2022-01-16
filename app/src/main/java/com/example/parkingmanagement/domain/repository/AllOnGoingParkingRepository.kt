package com.example.parkingmanagement.domain.repository

import com.example.parkingmanagement.domain.model.ParkingData


interface AllOnGoingParkingRepository  {

    suspend fun getAllOnGoingParking() : List<ParkingData>

    suspend fun depart(parkingData: ParkingData)

}
