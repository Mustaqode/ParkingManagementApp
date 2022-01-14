package com.example.parkingmanagement.domain.repository

import com.example.parkingmanagement.data.db.OnGoingParking


interface NewParkingRepository {

    suspend fun parkAVehicle(onGoingParking: OnGoingParking)

    suspend fun fetchCouponDetail() : String

}
