package com.example.parkingmanagement.di

import android.app.Application
import androidx.room.Room
import com.example.parkingmanagement.data.db.ParkingManagementAppDatabase
import com.example.parkingmanagement.data.repository.*
import com.example.parkingmanagement.domain.repository.*
import com.example.parkingmanagement.domain.use_case.allongoingparking.DepartUseCase
import com.example.parkingmanagement.domain.use_case.allongoingparking.GetAllOnGoingParkingUseCase
import com.example.parkingmanagement.domain.use_case.allongoingreservation.GetAllOnGoingReservationUseCase
import com.example.parkingmanagement.domain.use_case.allongoingreservation.UnReserveUseCase
import com.example.parkingmanagement.domain.use_case.alltransactions.GetAllTransactionsUseCase
import com.example.parkingmanagement.domain.use_case.entrance.GetExistingParkingSpaceUseCase
import com.example.parkingmanagement.domain.use_case.entrance.ResetAllDataUseCase
import com.example.parkingmanagement.domain.use_case.home.GetParkingSpaceDataUseCase
import com.example.parkingmanagement.domain.use_case.newparking.AddANewParkingUseCase
import com.example.parkingmanagement.domain.use_case.newparking.FetchCouponDetailUseCase
import com.example.parkingmanagement.domain.use_case.newparkingspace.AddANewParkingSpaceUseCase
import com.example.parkingmanagement.domain.use_case.newreservation.MakeANewReservationUseCase
import com.example.parkingmanagement.presentation.ui.allparking.AllParkingViewModel
import com.example.parkingmanagement.presentation.ui.allreservation.AllReservationViewModel
import com.example.parkingmanagement.presentation.ui.entrance.EntranceViewModel
import com.example.parkingmanagement.presentation.ui.home.HomeViewModel
import com.example.parkingmanagement.presentation.ui.newparking.NewParkingViewModel
import com.example.parkingmanagement.presentation.ui.newparkingspace.NewParkingSpaceViewModel
import com.example.parkingmanagement.presentation.ui.newreservation.NewReservationViewModel
import com.example.parkingmanagement.presentation.ui.transactions.AllTransactionsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { EntranceViewModel(get(), get()) }
    viewModel { NewParkingSpaceViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { AllParkingViewModel(get(), get()) }
    viewModel { AllReservationViewModel(get(), get()) }
    viewModel { NewParkingViewModel(get(), get()) }
    viewModel { NewReservationViewModel(get(), get()) }
    viewModel { AllTransactionsViewModel(get()) }
}

val repositoryModule = module {
    single<EntranceRepository> { EntranceRepositoryImpl(get()) }
    single<NewParkingSpaceRepository> { NewParkingSpaceRepositoryImpl(get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
    single<NewParkingRepository> { NewParkingRepositoryImpl(get()) }
    single<AllOnGoingParkingRepository> { AllOnGoingParkingRepositoryImpl(get()) }
    single<NewReservationRepository> { NewReservationRepositoryImpl(get()) }
    single<AllOngoingReservationRepository> { AllOngoingReservationRepositoryImpl(get()) }
    single<AllTransactionsRepository> { AllTransactionsRepositoryImpl(get()) }
}

val useCaseModule = module {
    single { GetExistingParkingSpaceUseCase(get()) }
    single { ResetAllDataUseCase(get()) }
    single { GetParkingSpaceDataUseCase(get()) }
    single { AddANewParkingUseCase(get()) }
    single { FetchCouponDetailUseCase(get()) }
    single { AddANewParkingSpaceUseCase(get()) }
    single { DepartUseCase(get()) }
    single { GetAllOnGoingParkingUseCase(get()) }
    single { MakeANewReservationUseCase(get()) }
    single { GetAllOnGoingReservationUseCase(get()) }
    single { UnReserveUseCase(get()) }
    single { GetAllTransactionsUseCase(get()) }

}

val databaseModule = module {
    single { provideDatabase(androidApplication()) }
}


private fun provideDatabase(application: Application): ParkingManagementAppDatabase {
    return Room.databaseBuilder(
        application,
        ParkingManagementAppDatabase::class.java,
        "parkingManagementAppDatabase"
    )
        .fallbackToDestructiveMigration()
        .build()
}

