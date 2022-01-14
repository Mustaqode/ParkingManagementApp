package com.example.parkingmanagement.domain.repository

import com.example.parkingmanagement.data.db.OnGoingReservation

interface AllOngoingReservationRepository {

    suspend fun getAllOnGoingReservation() : List<OnGoingReservation>

    suspend fun unReserve(onGoingReservation: OnGoingReservation, force: Boolean = false)
}
