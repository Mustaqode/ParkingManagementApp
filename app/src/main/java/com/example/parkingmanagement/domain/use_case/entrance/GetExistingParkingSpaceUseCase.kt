package com.example.parkingmanagement.domain.use_case.entrance

import com.example.parkingmanagement.common.Envelope
import com.example.parkingmanagement.data.db.ParkingSpace
import com.example.parkingmanagement.domain.repository.EntranceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetExistingParkingSpaceUseCase(private val entranceRepository: EntranceRepository) {

    operator fun invoke() : Flow<Envelope<ParkingSpace?>> =
        flow {
            try {
                emit(Envelope.Loading())
                emit(Envelope.Success(entranceRepository.getParkingSpaceData()))
            } catch (e: Exception) {
                emit(Envelope.Failure(e.message.toString()))
            }
        }

}
