package com.example.parkingmanagement.domain.repository

import com.example.parkingmanagement.data.db.OnGoingParking


interface AllOnGoingParkingRepository  {

    suspend fun getAllOnGoingParking() : List<OnGoingParking>

    suspend fun depart(onGoingParking: OnGoingParking)

}
