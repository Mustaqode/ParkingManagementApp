package com.example.parkingmanagement.domain.use_case.entrance

import com.example.parkingmanagement.common.Envelope
import com.example.parkingmanagement.domain.repository.EntranceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class ResetAllDataUseCase (private val entranceRepository: EntranceRepository) {

    operator fun invoke() : Flow<Envelope<Unit>> =
        flow {
            try {
                emit(Envelope.Loading())
                emit(Envelope.Success(entranceRepository.deleteAllData()))
            } catch (e: Exception) {
                emit(Envelope.Failure(e.message.toString()))
            }
        }

}
