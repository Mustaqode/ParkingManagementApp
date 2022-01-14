package com.example.parkingmanagement.common.helper


/**
 * Based on Largest Reminder Method
 *
 */
object ObjectAllocatorBasedOnPercentage {

    fun allocate(total: Int, percentages: List<Int>): ArrayList<Int> {

        val integerList = arrayListOf<Int>()
        val decimalList = arrayListOf<Double>()

        /**
         * Finding the object count based on percentage (i.e. 40 % of totalObject) for
         * all percentages passed and creating two different lists separating the integer value and
         * decimal value from each.
         */
        for (percentage in percentages) {
            val (percentageInteger, percentageDecimal) = percentage.percentageOf(total)
                .separateIntegerAndDecimal()
            integerList.add(percentageInteger)
            decimalList.add(percentageDecimal)
        }

        /**
         * Sorting the decimal list by descending order
         */
        val sortedDecimalList = arrayListOf<Double>()
        sortedDecimalList.addAll(decimalList)
        sortedDecimalList.sortDescending()

        // It will not be equal to the total object
        val sumOfAllRoundedResult = integerList.sum()

        var resultTotal = sumOfAllRoundedResult
        val result = arrayListOf<Int>()
        val resultIndex = arrayListOf<Int>()

        /**
         * Add 1 to the sorted integer list until the total and the sum of all integers in the list matches
         * aka all the vehicles are allotted proper space almost with respect to the give percentage.
         */
        sortedDecimalList.forEach { sortedDecimal ->
            decimalList.forEachIndexed { i, decimal ->
                if(sortedDecimal == decimal && resultTotal < total) {
                    result.add(integerList[i] + 1)
                    resultIndex.add(i)
                    resultTotal++
                }
            }
        }

        /**
         * Add the remaining item to the result with the help of the
         * indices saved during the above operation
         */
        for (i in integerList.indices) {
            if (i !in resultIndex) {
                result.add(integerList[i])
//                resultIndex.add(i)
            }
        }

        /**
         * Result will be like:
         * If the inputs are 10, listOf(40, 40, 20),
         * the result will be 4, 4, 2
         * If the inputs are 11, listOf(40, 40, 20),
         * the result will be 4, 5, 2
         */
        return result
    }
}
