package com.example.parkingmanagement

import android.app.Application
import com.example.parkingmanagement.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class ParkingManagementApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@ParkingManagementApp)
            modules(listOf(
                databaseModule,
                viewModelModule,
                repositoryModule
            ))
        }
    }
}
