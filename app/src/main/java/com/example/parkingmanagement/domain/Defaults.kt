package com.example.parkingmanagement.domain


object Defaults {

    /**
     * Allocation in percentage
     */
    private const val PERCENTAGE_OF_ALLOCATION_FOR_CARS = 40
    private const val PERCENTAGE_OF_ALLOCATION_FOR_BIKES= 40
    private const val PERCENTAGE_OF_ALLOCATION_FOR_BUSES = 20

    val listOfAllocatedParkingSlotPercentages = listOf(
        PERCENTAGE_OF_ALLOCATION_FOR_CARS, PERCENTAGE_OF_ALLOCATION_FOR_BIKES, PERCENTAGE_OF_ALLOCATION_FOR_BUSES
    )

    /**
     * Parking Fee
     */
    const val FIRST_HOUR_FEE = 10.0
    const val REMAINING_HOUR_FEE = 5.0
    const val FIRST_HOUR_DISCOUNT_FOR_THE_FIRST_TIME_USER = 50 // In Percentage
    const val REMAINING_HOURS_DISCOUNT_FOR_THE_FIRST_TIME_USER = 10 // In Percentage

}
