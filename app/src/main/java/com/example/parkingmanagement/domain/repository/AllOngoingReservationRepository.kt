package com.example.parkingmanagement.domain.repository

import com.example.parkingmanagement.domain.model.ReservationData

interface AllOngoingReservationRepository {

    suspend fun getAllOnGoingReservation() : List<ReservationData>

    suspend fun unReserve(reservationData: ReservationData)
}
