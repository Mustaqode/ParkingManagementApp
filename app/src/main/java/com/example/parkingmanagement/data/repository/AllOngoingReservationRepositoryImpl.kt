package com.example.parkingmanagement.data.repository

import com.example.parkingmanagement.data.db.ParkingManagementAppDatabase
import com.example.parkingmanagement.data.db.TransactionSummary
import com.example.parkingmanagement.data.db.VehicleType
import com.example.parkingmanagement.data.mapper.ParkingTransactionCostMapper
import com.example.parkingmanagement.data.mapper.ReservationDataMapper
import com.example.parkingmanagement.data.mapper.ReservationDataMapper.mapToReservationData
import com.example.parkingmanagement.domain.model.ReservationData
import com.example.parkingmanagement.domain.repository.AllOngoingReservationRepository
import kotlin.math.abs


class AllOngoingReservationRepositoryImpl(private val database: ParkingManagementAppDatabase) :
    AllOngoingReservationRepository {

    override suspend fun getAllOnGoingReservation(): List<ReservationData> {
        val allOnGoingReservation = database.getAllOnGoingReservation().toCollection(ArrayList())
        allOnGoingReservation.forEachIndexed { index, reservation ->
            if (checkTheReservedTimeIsOver(reservation.timeOfParking)) {
                unReserveTheParking(reservation.mapToReservationData(), false )
                allOnGoingReservation.removeAt(index)
            }
        }
        return ReservationDataMapper.map(allOnGoingReservation)
    }

    override suspend fun unReserve(reservationData: ReservationData) {
            unReserveTheParking(reservationData, true)
    }

    private suspend fun unReserveTheParking(
        reservationData: ReservationData,
        force: Boolean
    ) {
        database.deleteAnOnGoingReservation(reservationData.vehicleNumber)
        markParkingSpotVacant(reservationData)
        addANewTransactionSummary(reservationData, force)
    }

    private fun checkTheReservedTimeIsOver(timeOfParking: Long): Boolean {
        val timeDifference: Long = abs(timeOfParking - System.currentTimeMillis())
        val differenceInMinutes = ((timeDifference / 1000) / 60).toInt()

        // Every minute is equivalent to an hour for demo purpose
        return differenceInMinutes > NO_OF_HOURS_FOR_ONE_DAY_RESERVATION
    }


    /**
     * One day reservation is equal to 12 hours for now
     * The cost will be calculated based on that
     */
    private suspend fun addANewTransactionSummary(
        reservationData: ReservationData,
        force: Boolean
    ) {
        val (totalCost, noOfHours) = if (force) {
            ParkingTransactionCostMapper.map(
                reservationData.timeOfParking,
                reservationData.isFirstTime,
                System.currentTimeMillis()
            )
        } else {
            ParkingTransactionCostMapper.map(
                reservationData.isFirstTime,
                NO_OF_HOURS_FOR_ONE_DAY_RESERVATION
            )
        }

        database.addANewTransactionSummary(
            TransactionSummary(
                floorNumber = reservationData.floorNumber,
                vehicleNumber = reservationData.vehicleNumber,
                vehicleType = reservationData.vehicleType,
                noOfHours = noOfHours,
                isCouponApplied = reservationData.isFirstTime,
                isReservation = true,
                totalCost = totalCost
            )
        )
    }

    private suspend fun markParkingSpotVacant(reservationData: ReservationData) {
        when (reservationData.vehicleType.lowercase()) {
            VehicleType.CAR.type ->
                database.markAParkingSpaceForCarVacant(reservationData.floorNumber)
            VehicleType.BIKE.type ->
                database.markAParkingSpaceForBikeVacant(reservationData.floorNumber)
            VehicleType.BUS.type ->
                database.markAParkingSpaceForBusVacant(reservationData.floorNumber)
        }
    }

    companion object {
        private const val NO_OF_HOURS_FOR_ONE_DAY_RESERVATION = 12
    }
}
