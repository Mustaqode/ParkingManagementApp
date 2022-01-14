package com.example.parkingmanagement.data.mapper

import com.example.parkingmanagement.common.helper.ObjectAllocatorBasedOnPercentage
import com.example.parkingmanagement.data.db.ParkingSpace
import com.example.parkingmanagement.domain.Defaults


object ParkingSpaceAllotmentMapper {

    fun map(parkingSpace: ParkingSpace): ParkingSpace {
        val allocatedList =
            ObjectAllocatorBasedOnPercentage
                .allocate(
                    parkingSpace.totalFloor ?: 0,
                    Defaults.listOfAllocatedParkingSlotPercentages
                )
        allocatedList.sortDescending() // (40, 40, 20) equivalent slots will be taken

        return parkingSpace.copy(
            noOfSpacesForBikeEachFloor = allocatedList[0],
            noOfSpacesForCarEachFloor = allocatedList[1],
            noOfSpacesForBusEachFloor = allocatedList[2]
        )
    }

}
