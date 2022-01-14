package com.example.parkingmanagement.domain.use_case.newparking

import com.example.parkingmanagement.common.Envelope
import com.example.parkingmanagement.domain.repository.NewParkingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FetchCouponDetailUseCase(
    private val newParkingRepository: NewParkingRepository
) {
    operator fun invoke(): Flow<Envelope<String>> = flow {
        try {
            emit(Envelope.Loading())
            emit(Envelope.Success(newParkingRepository.fetchCouponDetail()))
        } catch (e: Exception) {
            emit(Envelope.Failure(e.message.toString()))
        }
    }

}
