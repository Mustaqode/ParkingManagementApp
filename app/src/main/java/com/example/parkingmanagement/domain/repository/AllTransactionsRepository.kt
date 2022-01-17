package com.example.parkingmanagement.domain.repository

import com.example.parkingmanagement.domain.model.TransactionSummary


interface AllTransactionsRepository {

    suspend fun getAllTransactions() : List<TransactionSummary>

}
