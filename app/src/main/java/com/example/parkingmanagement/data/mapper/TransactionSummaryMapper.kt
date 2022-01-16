package com.example.parkingmanagement.data.mapper

import com.example.parkingmanagement.data.db.TransactionSummary
import com.example.parkingmanagement.domain.model.TransactionSummary as TransactionSummaryMapped


object TransactionSummaryMapper {

    private const val OFFER_COUPON_APPLIED = "Offer Coupon Applied"
    private const val RESERVATION = " (Reservation) "
    private const val NONE = "None"

    fun map(transactionSummaryList: List<TransactionSummary>): List<TransactionSummaryMapped> {
        val mappedTransactionSummaries = arrayListOf<TransactionSummaryMapped>()
        transactionSummaryList.forEach {
            mappedTransactionSummaries.add(it.mapToTransactionSummary())
        }
        return mappedTransactionSummaries
    }

    private fun TransactionSummary.mapToTransactionSummary() =
        TransactionSummaryMapped(
            transactionId = this._id.toString(),
            vehicleNumber = this.vehicleNumber,
            vehicleType = this.vehicleType,
            floorNumber = this.floorNumber,
            isCouponApplied = if (this.isCouponApplied) OFFER_COUPON_APPLIED else NONE,
            totalCost = totalCost,
            noOfHours = if (isReservation) noOfHours.toString() + RESERVATION else noOfHours.toString()
        )

}

