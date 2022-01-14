package com.example.parkingmanagement.data.repository

import com.example.parkingmanagement.data.db.*
import com.example.parkingmanagement.data.mapper.ParkingTransactionCostMapper
import com.example.parkingmanagement.domain.repository.AllOnGoingParkingRepository


class AllOnGoingParkingRepositoryImpl(private val database: ParkingManagementAppDatabase) :
    AllOnGoingParkingRepository {

    override suspend fun getAllOnGoingParking(): List<OnGoingParking> =
        database.getAllOnGoingParking()

    /**
     * 1) Delete the entry from onGoingParking Table
     * 2) Empty the occupied spot (AvailableParkingSpace Table)
     * 3) Add a transaction
     */
    override suspend fun depart(onGoingParking: OnGoingParking) {
        database.deleteAnOnGoingParking(onGoingParking.vehicleNumber)
        markParkingSpotVacant(onGoingParking)
        addANewTransactionSummary(onGoingParking)
    }

    private suspend fun addANewTransactionSummary(onGoingParking: OnGoingParking) {
        val (totalCost, noOfHours) = ParkingTransactionCostMapper.map(
            onGoingParking.timeOfParking,
            onGoingParking.isFirstTime ?: false,
            System.currentTimeMillis()
        )

        database.addANewTransactionSummary(
            TransactionSummary(
                floorNumber = onGoingParking.floorNumber,
                vehicleNumber = onGoingParking.vehicleNumber,
                vehicleType = onGoingParking.vehicleType,
                noOfHours = noOfHours,
                isCouponApplied = onGoingParking.isFirstTime,
                isReservation = true,
                totalCost = totalCost
            )
        )
    }

    private suspend fun markParkingSpotVacant(onGoingParking: OnGoingParking) {
        when (onGoingParking.vehicleType) {
            VehicleType.CAR.type ->
                database.markAParkingSpaceForCarVacant(onGoingParking.floorNumber!!)
            VehicleType.BIKE.type ->
                database.markAParkingSpaceForBikeVacant(onGoingParking.floorNumber!!)
            VehicleType.BUS.type ->
                database.markAParkingSpaceForBusVacant(onGoingParking.floorNumber!!)
        }
    }
}
