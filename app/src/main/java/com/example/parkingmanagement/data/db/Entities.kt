package com.example.parkingmanagement.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = ParkingManagementAppDatabase.TABLE_NAME_PARKING_SPACE_DETAIL)
data class ParkingSpace(
    @PrimaryKey(autoGenerate = true)
    var _id: Int?,
    @ColumnInfo(name = "totalFloor") val totalFloor: Int?,
    @ColumnInfo(name = "parkingSpaceEachFloor") val parkingSpaceEachFloor: Int?,
    @ColumnInfo(name = "noOfSpacesForCarEachFloor") val noOfSpacesForCarEachFloor: Int?,
    @ColumnInfo(name = "noOfSpacesForBikeEachFloor") val noOfSpacesForBikeEachFloor: Int?,
    @ColumnInfo(name = "noOfSpacesForBusEachFloor") val noOfSpacesForBusEachFloor: Int?

) {
    @Ignore
    constructor(
        totalFloor: Int?,
        parkingSpaceEachFloor: Int?,
        noOfSpacesForCarEachFloor: Int? = null,
        noOfSpacesForBikeEachFloor: Int? = null,
        noOfSpacesForBusEachFloor: Int? = null
    ) : this(
        null,
        totalFloor,
        parkingSpaceEachFloor,
        noOfSpacesForCarEachFloor,
        noOfSpacesForBikeEachFloor,
        noOfSpacesForBusEachFloor
    )
}

@Entity(tableName = ParkingManagementAppDatabase.TABLE_NAME_AVAILABLE_PARKING_SPACE)
data class AvailableParkingSpace(
    @PrimaryKey
    @ColumnInfo(name = "floorNumber") val floorNumber: Int,
    @ColumnInfo(name = "availableSpacesForCars") val availableSpacesForCars: Int,
    @ColumnInfo(name = "availableSpacesForBikes") val availableSpacesForBikes: Int,
    @ColumnInfo(name = "availableSpacesForBuses") val availableSpacesForBuses: Int
)

@Entity(tableName = ParkingManagementAppDatabase.TABLE_NAME_ONGOING_PARKING)
data class OnGoingParking(
    @PrimaryKey
    @ColumnInfo(name = "vehicleNumber") val vehicleNumber: String,
    @ColumnInfo(name = "floorNumber") val floorNumber: Int? = null,
    @ColumnInfo(name = "vehicleType") val vehicleType: String,
    @ColumnInfo(name = "timeOfParking") val timeOfParking: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "isFirstTime") val isFirstTime: Boolean? = null
)

@Entity(tableName = ParkingManagementAppDatabase.TABLE_NAME_ONGOING_RESERVATION)
data class OnGoingReservation(
    @PrimaryKey
    @ColumnInfo(name = "vehicleNumber") val vehicleNumber: String,
    @ColumnInfo(name = "floorNumber") val floorNumber: Int? = null,
    @ColumnInfo(name = "vehicleType") val vehicleType: String,
    @ColumnInfo(name = "timeOfParking") val timeOfParking: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "isFirstTime") val isFirstTime: Boolean? = null,
    @ColumnInfo(name = "noOfHours") val noOfHours: Int? = null,
)

@Entity(tableName = ParkingManagementAppDatabase.TABLE_NAME_TRANSACTION_SUMMARY)
data class TransactionSummary(
    @PrimaryKey(autoGenerate = true)
    val _id: Int?,
    @ColumnInfo(name = "vehicleNumber") val vehicleNumber: String,
    @ColumnInfo(name = "floorNumber") val floorNumber: Int,
    @ColumnInfo(name = "vehicleType") val vehicleType: String,
    @ColumnInfo(name = "noOfHours") val noOfHours: Int,
    @ColumnInfo(name = "isCouponApplied") val isCouponApplied: Boolean,
    @ColumnInfo(name = "isReservation") val isReservation: Boolean,
    @ColumnInfo(name = "totalCost") val totalCost: String
) {
    @Ignore
    constructor(
        vehicleNumber: String,
        floorNumber: Int,
        vehicleType: String,
        noOfHours: Int,
        isCouponApplied: Boolean,
        isReservation: Boolean,
        totalCost: String
    ) : this(
        null,
        vehicleNumber,
        floorNumber,
        vehicleType,
        noOfHours,
        isCouponApplied,
        isReservation,
        totalCost
    )
}

@Entity(tableName = ParkingManagementAppDatabase.TABLE_NAME_VEHICLE_NUMBER_REGISTRY)
data class VehicleNumberRegistry(
    @PrimaryKey
    @ColumnInfo(name = "vehicleNumber") val vehicleNumber: String
)

