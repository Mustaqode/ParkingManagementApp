package com.example.parkingmanagement.data.repository

import com.example.parkingmanagement.data.db.ParkingManagementAppDatabase
import com.example.parkingmanagement.data.mapper.TransactionSummaryMapper
import com.example.parkingmanagement.domain.repository.AllTransactionsRepository


class AllTransactionsRepositoryImpl(
    private val database: ParkingManagementAppDatabase
) : AllTransactionsRepository {

    override suspend fun getAllTransactions()  =
        TransactionSummaryMapper.map(database.getAllTransactionSummary())
}
