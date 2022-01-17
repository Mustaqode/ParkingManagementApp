package com.example.parkingmanagement.domain.use_case.alltransactions

import com.example.parkingmanagement.common.Envelope
import com.example.parkingmanagement.domain.model.TransactionSummary
import com.example.parkingmanagement.domain.repository.AllTransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetAllTransactionsUseCase(
    private val allTransactionsRepository: AllTransactionsRepository
) {

    operator fun invoke() : Flow<Envelope<List<TransactionSummary>>> = flow {
        try {
            emit(Envelope.Loading())
            emit(Envelope.Success(allTransactionsRepository.getAllTransactions().reversed()))
        } catch (e: Exception) {
            emit(Envelope.Failure(e.message.toString()))
        }
    }
}
