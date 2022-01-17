package com.example.parkingmanagement.data.mapper

import com.example.parkingmanagement.common.helper.percentageOf
import com.example.parkingmanagement.common.helper.toAmericanCurrency
import com.example.parkingmanagement.data.db.OnGoingParking
import com.example.parkingmanagement.data.db.OnGoingReservation
import com.example.parkingmanagement.domain.Defaults
import com.example.parkingmanagement.domain.Defaults.REMAINING_HOUR_FEE
import kotlin.math.abs


object ParkingTransactionCostMapper {

    /**
     * For Parking
     * Return a pair of total cost and the no. of hours parked
     * Note: Each minute is considered as hour here for simulation.
     */
    fun map(timeOfParking: Long, isFirstTime: Boolean, departTime: Long): Pair<String, Int> {
        val timeDifference: Long = abs(timeOfParking - departTime)
        val differenceInMinutes = ((timeDifference / 1000) / 60).toInt()

        val firstHourCost = if (isFirstTime) {
            Defaults.FIRST_HOUR_DISCOUNT_FOR_THE_FIRST_TIME_USER.percentageOf(Defaults.FIRST_HOUR_FEE)
        } else Defaults.FIRST_HOUR_FEE

        val remainingHourCost = if (isFirstTime) {
            val offerAmount =
                Defaults.REMAINING_HOURS_DISCOUNT_FOR_THE_FIRST_TIME_USER.percentageOf(
                    REMAINING_HOUR_FEE
                )
            REMAINING_HOUR_FEE - offerAmount
        } else Defaults.REMAINING_HOUR_FEE

        val transactionCost =
            if (differenceInMinutes == 0) firstHourCost else firstHourCost + (remainingHourCost * (differenceInMinutes - 1))
        val transactionCostInCurrencyFormat = transactionCost.toAmericanCurrency()

        return transactionCostInCurrencyFormat to differenceInMinutes
    }

    /**
     * For Reservation
     * Return a pair of total cost and the no. of hours parked
     * Note: Each minute is considered as hour here for simulation.
     */
    fun map(isFirstTime: Boolean, noOfHours: Int): Pair<String, Int> {

        val firstHourCost = if (isFirstTime) {
            Defaults.FIRST_HOUR_DISCOUNT_FOR_THE_FIRST_TIME_USER.percentageOf(Defaults.FIRST_HOUR_FEE)
        } else Defaults.FIRST_HOUR_FEE

        val remainingHourCost = if (isFirstTime) {
            val offerAmount =
                Defaults.REMAINING_HOURS_DISCOUNT_FOR_THE_FIRST_TIME_USER.percentageOf(
                    REMAINING_HOUR_FEE
                )
            REMAINING_HOUR_FEE - offerAmount
        } else REMAINING_HOUR_FEE

        val transactionCost = firstHourCost + (remainingHourCost * (noOfHours - 1))
        val transactionCostInCurrencyFormat = transactionCost.toAmericanCurrency()

        return transactionCostInCurrencyFormat to noOfHours
    }

}
