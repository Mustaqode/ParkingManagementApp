package com.example.parkingmanagement.data.db

import androidx.room.*
import com.example.parkingmanagement.data.db.ParkingManagementAppDatabase.Companion.TABLE_NAME_AVAILABLE_PARKING_SPACE
import com.example.parkingmanagement.data.db.ParkingManagementAppDatabase.Companion.TABLE_NAME_ONGOING_PARKING
import com.example.parkingmanagement.data.db.ParkingManagementAppDatabase.Companion.TABLE_NAME_ONGOING_RESERVATION
import com.example.parkingmanagement.data.db.ParkingManagementAppDatabase.Companion.TABLE_NAME_PARKING_SPACE_DETAIL
import com.example.parkingmanagement.data.db.ParkingManagementAppDatabase.Companion.TABLE_NAME_TRANSACTION_SUMMARY
import com.example.parkingmanagement.data.db.ParkingManagementAppDatabase.Companion.TABLE_NAME_VEHICLE_NUMBER_REGISTRY

@Dao
interface ParkingSpaceDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addANewParkingSpace(parkingSpace: ParkingSpace)

    @Query("select * from $TABLE_NAME_PARKING_SPACE_DETAIL")
    suspend fun getAllParkingSpaces() : List<ParkingSpace>

    @Query("delete from $TABLE_NAME_PARKING_SPACE_DETAIL" )
    suspend fun deleteAllParkingSpaces()

}

@Dao
interface AvailableParkingSpaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewParkingSpace(availableParkingSpace: AvailableParkingSpace)

    @Query("select * from $TABLE_NAME_AVAILABLE_PARKING_SPACE")
    suspend fun getAllAvailableParkingSpaces() : List<AvailableParkingSpace>

    @Query("delete from $TABLE_NAME_AVAILABLE_PARKING_SPACE" )
    suspend fun deleteAllParkingSpaces()

    @Query("update $TABLE_NAME_AVAILABLE_PARKING_SPACE set availableSpacesForCars = availableSpacesForCars - 1 where floorNumber = :floorNo")
    suspend fun fillAnAvailableParkingSpaceForCar(floorNo : Int)

    @Query("update $TABLE_NAME_AVAILABLE_PARKING_SPACE set availableSpacesForBikes= availableSpacesForBikes - 1 where floorNumber = :floorNo")
    suspend fun fillAnAvailableParkingSpaceForBike(floorNo : Int)

    @Query("update $TABLE_NAME_AVAILABLE_PARKING_SPACE set availableSpacesForBuses = availableSpacesForBuses - 1 where floorNumber = :floorNo")
    suspend fun fillAnAvailableParkingSpaceForBus(floorNo : Int)

    @Query("update $TABLE_NAME_AVAILABLE_PARKING_SPACE set availableSpacesForCars = availableSpacesForCars + 1 where floorNumber = :floorNo")
    suspend fun markAParkingSpaceForCarVacant(floorNo : Int)

    @Query("update $TABLE_NAME_AVAILABLE_PARKING_SPACE set availableSpacesForBikes= availableSpacesForBikes + 1 where floorNumber = :floorNo")
    suspend fun markAParkingSpaceForBikeVacant(floorNo : Int)

    @Query("update $TABLE_NAME_AVAILABLE_PARKING_SPACE set availableSpacesForBuses = availableSpacesForBuses + 1 where floorNumber = :floorNo")
    suspend fun markAParkingSpaceForBusVacant(floorNo : Int)

    @Query("select * from $TABLE_NAME_AVAILABLE_PARKING_SPACE where floorNumber = :floorNo")
    suspend fun getNumberOfParkingSpaceAvailableOnAFloor(floorNo: Int) : List<AvailableParkingSpace>

}

@Dao
interface OnGoingParkingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addANewParking(parking: OnGoingParking)

    @Query("select * from $TABLE_NAME_ONGOING_PARKING")
    suspend fun getAllOnGoingParking() : List<OnGoingParking>

    @Query("delete from $TABLE_NAME_ONGOING_PARKING where vehicleNumber = :vehicleNumber")
    suspend fun deleteAnOnGoingParking(vehicleNumber: String)

    @Query("delete from $TABLE_NAME_ONGOING_PARKING")
    suspend fun deleteAllOnGoingParking()

}

@Dao
interface OnGoingReservationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addANewReservation(reservation: OnGoingReservation)

    @Query("select * from $TABLE_NAME_ONGOING_RESERVATION")
    suspend fun getAllOnGoingReservation() : List<OnGoingReservation>

    @Query("delete from $TABLE_NAME_ONGOING_RESERVATION where vehicleNumber = :vehicleNumber")
    suspend fun deleteAnOnGoingReservation(vehicleNumber: String)

    @Query("delete from $TABLE_NAME_ONGOING_RESERVATION")
    suspend fun deleteAllOnGoingReservation()

}

@Dao
interface VehicleNumberRegistryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addANewVehicleNumberToRegistry(vehicleNumber: VehicleNumberRegistry)

    @Query("delete from $TABLE_NAME_VEHICLE_NUMBER_REGISTRY")
    suspend fun deleteAllFromTheRegistry()

    @Query("select * from $TABLE_NAME_VEHICLE_NUMBER_REGISTRY")
    suspend fun getAllVehicleNumber() : List<VehicleNumberRegistry>

    @Query("select * from $TABLE_NAME_VEHICLE_NUMBER_REGISTRY where vehicleNumber = :vehicleNumber")
    suspend fun getVehicleNumberFromTheRegistry(vehicleNumber: String) : List<VehicleNumberRegistry>

}

@Dao
interface TransactionSummaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addANewTransactionSummary(summary: TransactionSummary)

    @Query("select * from $TABLE_NAME_TRANSACTION_SUMMARY")
    suspend fun getAllTransactionSummary() : List<TransactionSummary>

    @Query("delete from $TABLE_NAME_TRANSACTION_SUMMARY")
    suspend fun deleteAllTransactionSummary()

}



