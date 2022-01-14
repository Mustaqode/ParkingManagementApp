package com.example.parkingmanagement.domain.use_case.newparkingspace

import com.example.parkingmanagement.common.Envelope
import com.example.parkingmanagement.data.db.ParkingSpace
import com.example.parkingmanagement.domain.repository.NewParkingSpaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class AddANewParkingSpaceUseCase(private val newParkingSpaceRepository: NewParkingSpaceRepository) {

    operator fun invoke(parkingSpace: ParkingSpace): Flow<Envelope<Unit>> =
        flow {
            try {
                emit(Envelope.Loading())
                emit(Envelope.Success(newParkingSpaceRepository.addANewParkingSpace(parkingSpace)))
            } catch (e: Exception) {
                emit(Envelope.Failure(e.message.toString()))
            }
        }
}
