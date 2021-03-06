package com.example.parkingmanagement.domain.use_case.allongoingreservation

import com.example.parkingmanagement.common.Envelope
import com.example.parkingmanagement.data.db.OnGoingReservation
import com.example.parkingmanagement.domain.model.ReservationData
import com.example.parkingmanagement.domain.repository.AllOngoingReservationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetAllOnGoingReservationUseCase (
    private val allOnGoingReservationRepository: AllOngoingReservationRepository
) {

    operator fun invoke(): Flow<Envelope<List<ReservationData>>> =
        flow {
            try {
                emit(Envelope.Loading())
                emit(Envelope.Success(allOnGoingReservationRepository.getAllOnGoingReservation().reversed()))
            } catch (e: Exception) {
                emit(Envelope.Failure(e.message.toString()))
            }
        }

}
