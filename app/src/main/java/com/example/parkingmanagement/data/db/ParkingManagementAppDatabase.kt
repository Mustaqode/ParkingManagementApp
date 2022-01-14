package com.example.parkingmanagement.data.db

import androidx.room.*

@Database(
    entities = [
        ParkingSpace::class,
        AvailableParkingSpace::class,
        OnGoingParking::class,
        OnGoingReservation::class,
        VehicleNumberRegistry::class,
        TransactionSummary::class
    ], version = 1, exportSchema = false
)
abstract class ParkingManagementAppDatabase : RoomDatabase() {

    protected abstract fun parkingSpaceDao(): ParkingSpaceDetailDao

    protected abstract fun availableParkingSpaceDao(): AvailableParkingSpaceDao

    protected abstract fun onGoingParkingDao(): OnGoingParkingDao

    protected abstract fun onGoingReservationDao(): OnGoingReservationDao

    protected abstract fun vehicleNumberRegistryDao(): VehicleNumberRegistryDao

    protected abstract fun transactionSummaryDao(): TransactionSummaryDao

    /**
     * Parking Space Table
     */
    suspend fun addANewParkingSpace(parkingSpace: ParkingSpace) {
        parkingSpaceDao().addANewParkingSpace(parkingSpace)
    }

    suspend fun getAllParkingSpace(): List<ParkingSpace> =
        parkingSpaceDao().getAllParkingSpaces()

    suspend fun deleteAllParkingSpaces() {
        parkingSpaceDao().deleteAllParkingSpaces()
    }

    /**
     * Available Parking Space
     */
    suspend fun addNewParkingSpace(availableParkingSpace: AvailableParkingSpace) {
        availableParkingSpaceDao().addNewParkingSpace(availableParkingSpace)
    }

    suspend fun getAllAvailableParkingSpaces(): List<AvailableParkingSpace> =
        availableParkingSpaceDao().getAllAvailableParkingSpaces()

    suspend fun deleteAllAvailableParkingSpaces() {
        availableParkingSpaceDao().deleteAllParkingSpaces()
    }

    suspend fun fillAnAvailableParkingSpaceForCar(floorNo: Int) {
        availableParkingSpaceDao().fillAnAvailableParkingSpaceForCar(floorNo)
    }

    suspend fun fillAnAvailableParkingSpaceForBike(floorNo: Int) {
        availableParkingSpaceDao().fillAnAvailableParkingSpaceForBike(floorNo)
    }

    suspend fun fillAnAvailableParkingSpaceForBus(floorNo: Int) {
        availableParkingSpaceDao().fillAnAvailableParkingSpaceForBus(floorNo)
    }

    suspend fun markAParkingSpaceForCarVacant(floorNo: Int) {
        availableParkingSpaceDao().markAParkingSpaceForCarVacant(floorNo)
    }

    suspend fun markAParkingSpaceForBikeVacant(floorNo: Int) {
        availableParkingSpaceDao().markAParkingSpaceForBikeVacant(floorNo)
    }

    suspend fun markAParkingSpaceForBusVacant(floorNo: Int) {
        availableParkingSpaceDao().markAParkingSpaceForBusVacant(floorNo)
    }

    suspend fun getNumberOfParkingSpaceAvailableOnAFloor(floorNo: Int) =
        availableParkingSpaceDao().getNumberOfParkingSpaceAvailableOnAFloor(floorNo)

    /**
     * Ongoing parking
     */

    suspend fun addANewParking(parking: OnGoingParking) {
        onGoingParkingDao().addANewParking(parking)
    }

    suspend fun getAllOnGoingParking(): List<OnGoingParking> =
        onGoingParkingDao().getAllOnGoingParking()

    suspend fun deleteAnOnGoingParking(vehicleNumber: String) {
        onGoingParkingDao().deleteAnOnGoingParking(vehicleNumber)
    }

    suspend fun deleteAllOnGoingParking() {
        onGoingParkingDao().deleteAllOnGoingParking()
    }

    /**
     * Ongoing reservation
     */

    suspend fun addANewReservation(reservation: OnGoingReservation) {
        onGoingReservationDao().addANewReservation(reservation)
    }

    suspend fun getAllOnGoingReservation(): List<OnGoingReservation> =
        onGoingReservationDao().getAllOnGoingReservation()

    suspend fun deleteAnOnGoingReservation(vehicleNumber: String) {
        onGoingReservationDao().deleteAnOnGoingReservation(vehicleNumber)
    }

    suspend fun deleteAllOnGoingReservation() {
        onGoingReservationDao().deleteAllOnGoingReservation()
    }

    /**
     * Vehicle Number Registry
     */


    suspend fun addANewVehicleNumberToRegistry(vehicleNumber: VehicleNumberRegistry) {
        vehicleNumberRegistryDao().addANewVehicleNumberToRegistry(vehicleNumber)
    }

    suspend fun deleteAllFromTheRegistry() {
        vehicleNumberRegistryDao().deleteAllFromTheRegistry()
    }

    suspend fun getAllVehicleNumber(): List<VehicleNumberRegistry> =
        vehicleNumberRegistryDao().getAllVehicleNumber()

    suspend fun getVehicleNumberFromTheRegistry(vehicleNumber: String): List<VehicleNumberRegistry> =
        vehicleNumberRegistryDao().getVehicleNumberFromTheRegistry(vehicleNumber)


    /**
     * Transaction Summary
     */

    suspend fun addANewTransactionSummary(summary: TransactionSummary) {
        transactionSummaryDao().addANewTransactionSummary(summary)
    }

    suspend fun getAllTransactionSummary(): List<TransactionSummary> =
        transactionSummaryDao().getAllTransactionSummary()

    suspend fun deleteAllTransactionSummary() {
        transactionSummaryDao().deleteAllTransactionSummary()
    }


    companion object {
        const val TABLE_NAME_PARKING_SPACE_DETAIL = "table_name_parking_space_detail"
        const val TABLE_NAME_AVAILABLE_PARKING_SPACE = "table_name_available_parking_space"
        const val TABLE_NAME_ONGOING_PARKING = "table_name_ongoing_parking"
        const val TABLE_NAME_ONGOING_RESERVATION = "table_name_ongoing_reservation"
        const val TABLE_NAME_TRANSACTION_SUMMARY = "table_name_transaction_summary"
        const val TABLE_NAME_VEHICLE_NUMBER_REGISTRY = "table_name_vehicle_number_registry"
    }
}

