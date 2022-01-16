package com.example.parkingmanagement.data.repository

import com.example.parkingmanagement.data.db.OnGoingReservation
import com.example.parkingmanagement.data.db.ParkingManagementAppDatabase
import com.example.parkingmanagement.data.db.TransactionSummary
import com.example.parkingmanagement.data.db.VehicleType
import com.example.parkingmanagement.data.mapper.ParkingTransactionCostMapper
import com.example.parkingmanagement.data.mapper.ReservationDataMapper
import com.example.parkingmanagement.domain.model.ReservationData
import com.example.parkingmanagement.domain.repository.AllOngoingReservationRepository


class AllOngoingReservationRepositoryImpl(private val database: ParkingManagementAppDatabase) :
    AllOngoingReservationRepository {

    override suspend fun getAllOnGoingReservation(): List<ReservationData> =
        ReservationDataMapper.map(database.getAllOnGoingReservation())

    /**
     * If `force = true` it means user chose to unreserve before the chosen time.
     * Cost will be calculated only up to the time when user unreserved it.
     */
    override suspend fun unReserve(reservationData: ReservationData, force: Boolean) {
        if (force) {
            unReserveTheParking(reservationData, force)
        } else if (checkTheReservedTimeIsOver(reservationData)) {
            unReserveTheParking(reservationData, force)
        }
    }

    private suspend fun unReserveTheParking(
        reservationData: ReservationData,
        force: Boolean
    ) {
        database.deleteAnOnGoingReservation(reservationData.vehicleNumber)
        markParkingSpotVacant(reservationData)
        addANewTransactionSummary(reservationData, force)
    }

    private fun checkTheReservedTimeIsOver(reservationData: ReservationData): Boolean {
        val timeDifference: Long = reservationData.timeOfParking - System.currentTimeMillis()
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
                reservationData.timeOfParking,
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
        when (reservationData.vehicleType) {
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
