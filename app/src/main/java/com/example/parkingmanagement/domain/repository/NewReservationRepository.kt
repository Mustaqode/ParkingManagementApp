package com.example.parkingmanagement.domain.repository

import com.example.parkingmanagement.data.db.OnGoingReservation


interface NewReservationRepository {

    suspend fun makeAReservation(onGoingReservation: OnGoingReservation)

}
