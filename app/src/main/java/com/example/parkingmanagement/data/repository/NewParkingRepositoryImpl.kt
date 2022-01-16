package com.example.parkingmanagement.data.repository

import com.example.parkingmanagement.data.db.*
import com.example.parkingmanagement.domain.Defaults.FIRST_HOUR_DISCOUNT_FOR_THE_FIRST_TIME_USER
import com.example.parkingmanagement.domain.Defaults.FIRST_HOUR_FEE
import com.example.parkingmanagement.domain.Defaults.REMAINING_HOURS_DISCOUNT_FOR_THE_FIRST_TIME_USER
import com.example.parkingmanagement.domain.Defaults.REMAINING_HOUR_FEE
import com.example.parkingmanagement.domain.repository.NewParkingRepository
import kotlin.Exception


class NewParkingRepositoryImpl(private val database: ParkingManagementAppDatabase) :
    NewParkingRepository {

    /**
     *  1) Check if the vehicle no. is of the first time user.
     *  2) Make sure the vehicle no is not in the existing onGoingParking list
     *  2) Add an ongoing parking entry
     *  3) Update the nearby empty spot in `availableParkingSpace` table
     */
    override suspend fun parkAVehicle(onGoingParking: OnGoingParking) {
        checkIfTheVehicleIsAlreadyParked(onGoingParking)

        val nearbyFreeParkingSpace = getNearbyAvailableParkingSpace(onGoingParking.vehicleType)

        if (nearbyFreeParkingSpace != null) {
            val parking = onGoingParking.copy(
                floorNumber = nearbyFreeParkingSpace.floorNumber,
                isFirstTime = findIfThisIsTheFirstTimeUser(onGoingParking.vehicleNumber)
            )
            database.addANewParking(parking)
            database.addANewVehicleNumberToRegistry(VehicleNumberRegistry(vehicleNumber = onGoingParking.vehicleNumber))
            parkTheVehicleOnTheAvailableSpot(nearbyFreeParkingSpace, onGoingParking.vehicleType)
        } else throw Exception(ERROR_NO_PARKING_SPACE_AVAILABLE)
    }

    override suspend fun fetchCouponDetail(): String =
        "For the first time customers, $FIRST_HOUR_DISCOUNT_FOR_THE_FIRST_TIME_USER% " +
                "discount is available in the first hour fee ($ $FIRST_HOUR_FEE) " +
                "and $REMAINING_HOURS_DISCOUNT_FOR_THE_FIRST_TIME_USER% discount is available in the " +
                "remaining hours fee ($ $REMAINING_HOUR_FEE)."

    private suspend fun checkIfTheVehicleIsAlreadyParked(onGoingParking: OnGoingParking) {
        val allOnGoingAParking = database.getAllOnGoingParking()

        allOnGoingAParking.forEach {
            if (onGoingParking.vehicleNumber == it.vehicleNumber) {
                throw Exception(ERROR_THE_VEHICLE_IS_ALREADY_PARKED)
            }
        }
    }

    private suspend fun findIfThisIsTheFirstTimeUser(vehicleNumber: String): Boolean =
        database.getVehicleNumberFromTheRegistry(vehicleNumber).isEmpty()

    private suspend fun getNearbyAvailableParkingSpace(vehicleType: String): AvailableParkingSpace? {
        val allAvailableParkingSpaces = database.getAllAvailableParkingSpaces()

        val nearbyFreeParkingSpace: AvailableParkingSpace? = allAvailableParkingSpaces.find {
            when (vehicleType.lowercase()) {
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
        when (vehicleType.lowercase()) {
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
        private const val ERROR_THE_VEHICLE_IS_ALREADY_PARKED =
            "The vehicle with this reg.no. is already parked in our premise!"
    }

}
