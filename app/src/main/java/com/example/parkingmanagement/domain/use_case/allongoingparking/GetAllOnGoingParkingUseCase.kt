package com.example.parkingmanagement.domain.use_case.allongoingparking

import com.example.parkingmanagement.common.Envelope
import com.example.parkingmanagement.data.db.OnGoingParking
import com.example.parkingmanagement.domain.model.ParkingData
import com.example.parkingmanagement.domain.repository.AllOnGoingParkingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetAllOnGoingParkingUseCase(
    private val allOnGoingParkingRepository: AllOnGoingParkingRepository
) {

    operator fun invoke(): Flow<Envelope<List<ParkingData>>> =
        flow {
            try {
                emit(Envelope.Loading())
                emit(Envelope.Success(allOnGoingParkingRepository.getAllOnGoingParking().reversed()))
            } catch (e: Exception) {
                emit(Envelope.Failure(e.message.toString()))
            }
        }

}
