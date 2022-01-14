package com.example.parkingmanagement.common.helper


/**
 * Based on Largest Reminder Method
 *
 */
object ObjectAllocatorBasedOnPercentage {

    fun allocate(total: Int, percentages: List<Int>) : ArrayList<Int> {

        val integerList = arrayListOf<Int>()
        val decimalList = arrayListOf<Double>()

        /**
         * Finding the object count based on percentage (i.e. 40 % of totalObject) for
         * all percentages passed and creating two different lists separating the integer value and
         * decimal value from each.
         */
        for ( percentage in percentages) {
            val (percentageInteger, percentageDecimal) = percentage.percentageOf(total).separateIntegerAndDecimal()
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
        // Diff between total object and sum of all rounded up percentages
        val differenceBetweenRoundedResultAndTotal = sumOfAllRoundedResult - total

        val result = arrayListOf<Int>()
        val resultIndex = arrayListOf<Int>()

        /**
         * Adding `1` to each rounded up percentage until the sum matches the total and then
         * break the loop
         */
        for ( i in sortedDecimalList.indices) {
            if ((i+1) <= differenceBetweenRoundedResultAndTotal) {
                for (j in decimalList.indices) {
                    if (decimalList[j] == sortedDecimalList[i]) {
                        result.add(integerList[j] + 1)
                        resultIndex.add(j)
                    }
                }
            } else {
                break
            }
        }

        /**
         * Add the remaining item to the result with the help of the
         * indices saved during the above operation
         */
        for ( i in integerList.indices) {
            if(i !in resultIndex) {
                result.add(integerList[i])
//                resultIndex.add(i)
            }
        }

        /**
         * Result will be like:
         * If the inputs are 10, listOf(40, 40, 20),
         * the result will be 4, 4, 2
          */
        return result
    }
}
