package com.example.parkingmanagement.data.repository

import com.example.parkingmanagement.data.db.OnGoingReservation
import com.example.parkingmanagement.data.db.ParkingManagementAppDatabase
import com.example.parkingmanagement.data.db.TransactionSummary
import com.example.parkingmanagement.data.db.VehicleType
import com.example.parkingmanagement.data.mapper.ParkingTransactionCostMapper
import com.example.parkingmanagement.domain.repository.AllOngoingReservationRepository


class AllOngoingReservationRepositoryImpl(private val database: ParkingManagementAppDatabase) :
    AllOngoingReservationRepository {

    override suspend fun getAllOnGoingReservation(): List<OnGoingReservation> =
        database.getAllOnGoingReservation()

    /**
     * If `force = true` it means user chose to unreserve before the chosen time.
     * Cost will be calculated only up to the time when user unreserved it.
     */
    override suspend fun unReserve(onGoingReservation: OnGoingReservation, force: Boolean) {
        if (force) {
            unReserveTheParking(onGoingReservation, force)
        } else if (checkTheReservedTimeIsOver(onGoingReservation)) {
            unReserveTheParking(onGoingReservation, force)
        }
    }

    private suspend fun unReserveTheParking(
        onGoingReservation: OnGoingReservation,
        force: Boolean
    ) {
        database.deleteAnOnGoingReservation(onGoingReservation.vehicleNumber)
        markParkingSpotVacant(onGoingReservation)
        addANewTransactionSummary(onGoingReservation, force)
    }

    private fun checkTheReservedTimeIsOver(onGoingReservation: OnGoingReservation): Boolean {
        val timeDifference: Long = onGoingReservation.timeOfParking - System.currentTimeMillis()
        val differenceInMinutes = ((timeDifference / 1000) / 60).toInt()

        // Every minute is equivalent to an hour for demo purpose
        return differenceInMinutes > onGoingReservation.noOfHours
    }

    private suspend fun addANewTransactionSummary(
        onGoingReservation: OnGoingReservation,
        force: Boolean
    ) {
        val (totalCost, noOfHours) = if (force) {
            ParkingTransactionCostMapper.map(
                onGoingReservation.timeOfParking,
                onGoingReservation.isFirstTime ?: false,
                System.currentTimeMillis()
            )
        } else {
            ParkingTransactionCostMapper.map(
                onGoingReservation.timeOfParking,
                onGoingReservation.isFirstTime ?: false,
                onGoingReservation.noOfHours
            )
        }

        database.addANewTransactionSummary(
            TransactionSummary(
                floorNumber = onGoingReservation.floorNumber,
                vehicleNumber = onGoingReservation.vehicleNumber,
                vehicleType = onGoingReservation.vehicleType,
                noOfHours = noOfHours,
                isCouponApplied = onGoingReservation.isFirstTime,
                isReservation = true,
                totalCost = totalCost
            )
        )
    }

    private suspend fun markParkingSpotVacant(onGoingReservation: OnGoingReservation) {
        when (onGoingReservation.vehicleType) {
            VehicleType.CAR.type ->
                database.markAParkingSpaceForCarVacant(onGoingReservation.floorNumber!!)
            VehicleType.BIKE.type ->
                database.markAParkingSpaceForBikeVacant(onGoingReservation.floorNumber!!)
            VehicleType.BUS.type ->
                database.markAParkingSpaceForBusVacant(onGoingReservation.floorNumber!!)
        }
    }
}
