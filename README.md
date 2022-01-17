# ParkingManagementApp
** Parking Management android app for multi-storey parking building ** 

**Problem Statement In Brief:**

Build an android app which will let an user to create a parking space for a multistorey parking premise with basic UI and the following features: 

1) Allot each storey to accomodate car (40%) , bike (40%) and bus (20%) automatically based on user input. 
2) Show details about the parking space including the available parking spaces in the building for all given vehicle types.
3) Let users to park their vehicle (identifier is vehicle reg. no.) and depart whenever they want.
4) Allow users to make a full-day reservation and unreserve it whenever they want.
5) Add a fee on number of hours they have parked on the premise. (First hour fee and the remaining hours fee are different)
6) Identify the first time users and offer a discount on their transaction ( 50% off in the first hour fee and 10% off in the remaining hours fee)
7) Show transaction summaries to the user once they have departed / unreserved a spot. 
8) User can reset the entire data to create a new parking sapce.
8) For demo purpose each minute is considered to be one hour in the app. 

**Edge cases handled**

1) User cannot park the vehicle with the same reg. no twice.
2) User will be able to park the vehicle with the same reg. no only when the existing vehicle has been departed and 
   the next parking cost doesn't involve the discount.
3) If a full-day reservation is made and the user didn't interrupt it manually by unreserving it, then we will automatically remove the vehicle from the
   spot when the time is up and add a tranaction (full-day cost).
4) If an user unreserve the space before the day ends then he will be billed for the number of hours he's parked the car in the premise.    

**Language, Libraries, and Architecture**

1) Kotlin
2) MVVM Architecture (ViewModel, LiveData)
3) Room Db
4) Coroutines (Flow)
5) Koin for Dependency Injection

