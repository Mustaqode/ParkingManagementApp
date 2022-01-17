package com.example.parkingmanagement.domain.model


class TransactionSummary(
    val transactionId: String,
    val vehicleNumber: String,
    val floorNumber: Int,
    val vehicleType: String,
    val noOfHours: String,
    val isCouponApplied: String,
    val totalCost: String?
)
