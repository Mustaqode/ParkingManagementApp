package com.example.parkingmanagement.domain.use_case.newparking

import com.example.parkingmanagement.common.Envelope
import com.example.parkingmanagement.data.db.OnGoingParking
import com.example.parkingmanagement.domain.repository.NewParkingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class AddANewParkingUseCase(
    private val newParkingRepository: NewParkingRepository
) {

    operator fun invoke(onGoingParking: OnGoingParking) : Flow<Envelope<Unit>> = flow {
        try {
            emit(Envelope.Loading())
            emit(Envelope.Success(newParkingRepository.parkAVehicle(onGoingParking)))
        } catch (e: Exception) {
            emit(Envelope.Failure(e.message.toString()))
        }
    }

}
