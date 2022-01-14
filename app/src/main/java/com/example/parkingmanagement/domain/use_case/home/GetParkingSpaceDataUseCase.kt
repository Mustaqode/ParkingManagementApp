package com.example.parkingmanagement.domain.use_case.home

import com.example.parkingmanagement.common.Envelope
import com.example.parkingmanagement.domain.model.ParkingSpaceData
import com.example.parkingmanagement.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetParkingSpaceDataUseCase(
    private val homeRepository: HomeRepository
) {

    operator fun invoke() : Flow<Envelope<ParkingSpaceData>> = flow {
        try {
            emit(Envelope.Loading())
            emit(Envelope.Success(homeRepository.getParkingSpaceData()))
        } catch (e: Exception) {
            emit(Envelope.Failure(e.message.toString()))
        }
    }

}
