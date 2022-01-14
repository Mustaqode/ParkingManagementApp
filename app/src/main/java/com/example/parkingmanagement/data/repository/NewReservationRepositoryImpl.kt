package com.example.parkingmanagement.data.repository

import com.example.parkingmanagement.data.db.AvailableParkingSpace
import com.example.parkingmanagement.data.db.OnGoingReservation
import com.example.parkingmanagement.data.db.ParkingManagementAppDatabase
import com.example.parkingmanagement.data.db.VehicleType
import com.example.parkingmanagement.domain.Defaults
import com.example.parkingmanagement.domain.repository.NewReservationRepository
import java.lang.Exception


class NewReservationRepositoryImpl(
    private val database: ParkingManagementAppDatabase
) : NewReservationRepository {

    override suspend fun makeAReservation(onGoingReservation: OnGoingReservation) {
        val nearbyFreeParkingSpace = getNearbyAvailableParkingSpace(onGoingReservation.vehicleType)

        if (nearbyFreeParkingSpace != null) {
            val reservation = onGoingReservation.copy(
                floorNumber = nearbyFreeParkingSpace.floorNumber,
                isFirstTime = findIfThisIsTheFirstTimeUser(onGoingReservation.vehicleNumber)
            )
            database.addANewReservation(reservation)
            parkTheVehicleOnTheAvailableSpot(nearbyFreeParkingSpace, onGoingReservation.vehicleType)
        } else throw Exception(ERROR_NO_PARKING_SPACE_AVAILABLE)
    }

    override suspend fun fetchCouponDetail(): String =
        "For the first time customers, ${Defaults.FIRST_HOUR_DISCOUNT_FOR_THE_FIRST_TIME_USER}% " +
                "discount is available in the first hour fee (/$ ${Defaults.FIRST_HOUR_FEE}) " +
                "and ${Defaults.REMAINING_HOURS_DISCOUNT_FOR_THE_FIRST_TIME_USER} discount is available in the " +
                "remaining hours fee (/$ ${Defaults.REMAINING_HOUR_FEE})."

    private suspend fun findIfThisIsTheFirstTimeUser(vehicleNumber: String): Boolean =
        database.getVehicleNumberFromTheRegistry(vehicleNumber).isEmpty()

    private suspend fun getNearbyAvailableParkingSpace(vehicleType: String): AvailableParkingSpace? {
        val allAvailableParkingSpaces = database.getAllAvailableParkingSpaces()

        val nearbyFreeParkingSpace: AvailableParkingSpace? = allAvailableParkingSpaces.find {
            when (vehicleType) {
                VehicleType.CAR.type -> it.availableSpacesForCars != 0
                VehicleType.BIKE.type -> it.availableSpacesForBikes != 0
                else -> it.availableSpacesForBuses != 0
            }
        }
        return nearbyFreeParkingSpace
    }

    private suspend fun parkTheVehicleOnTheAvailableSpot(
        nearbyFreeParkingSpace: AvailableParkingSpace,
        vehicleType: String
    ) {
        when (vehicleType) {
            VehicleType.CAR.type -> database.fillAnAvailableParkingSpaceForCar(
                nearbyFreeParkingSpace.floorNumber
            )
            VehicleType.BIKE.type -> database.fillAnAvailableParkingSpaceForBike(
                nearbyFreeParkingSpace.floorNumber
            )
            else -> database.fillAnAvailableParkingSpaceForBus(
                nearbyFreeParkingSpace.floorNumber
            )
        }
    }

    companion object {
        private const val ERROR_NO_PARKING_SPACE_AVAILABLE = "No parking space is vacant for now!"
    }
}
