package com.example.parkingmanagement.domain.use_case.allongoingreservation

import com.example.parkingmanagement.common.Envelope
import com.example.parkingmanagement.data.db.OnGoingReservation
import com.example.parkingmanagement.domain.model.ReservationData
import com.example.parkingmanagement.domain.repository.AllOngoingReservationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class UnReserveUseCase(
    private val allOnGoingReservationRepository: AllOngoingReservationRepository
) {

    operator fun invoke(
        onGoingReservation: ReservationData
    ): Flow<Envelope<Unit>> =
        flow {
            try {
                emit(Envelope.Loading())
                emit(
                    Envelope.Success(
                        allOnGoingReservationRepository.unReserve(
                            onGoingReservation
                        )
                    )
                )
            } catch (e: Exception) {
                emit(Envelope.Failure(e.message.toString()))
            }
        }

}
