package com.example.parkingmanagement.data.repository

import com.example.parkingmanagement.data.db.*
import com.example.parkingmanagement.data.mapper.ParkingDataMapper
import com.example.parkingmanagement.data.mapper.ParkingTransactionCostMapper
import com.example.parkingmanagement.domain.model.ParkingData
import com.example.parkingmanagement.domain.repository.AllOnGoingParkingRepository


class AllOnGoingParkingRepositoryImpl(private val database: ParkingManagementAppDatabase) :
    AllOnGoingParkingRepository {

    override suspend fun getAllOnGoingParking(): List<ParkingData> =
         ParkingDataMapper.map(database.getAllOnGoingParking())

    /**
     * 1) Delete the entry from onGoingParking Table
     * 2) Empty the occupied spot (AvailableParkingSpace Table)
     * 3) Add a transaction
     */
    override suspend fun depart(parkingData: ParkingData) {
        database.deleteAnOnGoingParking(parkingData.vehicleNumber)
        markParkingSpotVacant(parkingData)
        addANewTransactionSummary(parkingData)
    }

    private suspend fun addANewTransactionSummary(parkingData: ParkingData) {
        val (totalCost, noOfHours) = ParkingTransactionCostMapper.map(
            parkingData.timeOfParking,
            parkingData.isFirstTime,
            System.currentTimeMillis()
        )

        database.addANewTransactionSummary(
            TransactionSummary(
                floorNumber = parkingData.floorNumber,
                vehicleNumber = parkingData.vehicleNumber,
                vehicleType = parkingData.vehicleType,
                noOfHours = noOfHours,
                isCouponApplied = parkingData.isFirstTime,
                isReservation = true,
                totalCost = totalCost
            )
        )
    }

    private suspend fun markParkingSpotVacant(parkingData: ParkingData) {
        when (parkingData.vehicleType) {
            VehicleType.CAR.type ->
                database.markAParkingSpaceForCarVacant(parkingData.floorNumber)
            VehicleType.BIKE.type ->
                database.markAParkingSpaceForBikeVacant(parkingData.floorNumber)
            VehicleType.BUS.type ->
                database.markAParkingSpaceForBusVacant(parkingData.floorNumber)
        }
    }
}
