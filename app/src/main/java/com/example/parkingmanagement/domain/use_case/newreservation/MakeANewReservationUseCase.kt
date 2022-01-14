package com.example.parkingmanagement.domain.use_case.newreservation

import com.example.parkingmanagement.common.Envelope
import com.example.parkingmanagement.data.db.OnGoingReservation
import com.example.parkingmanagement.domain.repository.NewReservationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class MakeANewReservationUseCase(
    private val newReservationRepository: NewReservationRepository
) {

    operator fun invoke(reservation: OnGoingReservation): Flow<Envelope<Unit>> =
        flow {
            try {
                emit(Envelope.Loading())
                emit(Envelope.Success(newReservationRepository.makeAReservation(reservation)))
            } catch (e: Exception) {
                emit(Envelope.Failure(e.message.toString()))
            }
        }
}
