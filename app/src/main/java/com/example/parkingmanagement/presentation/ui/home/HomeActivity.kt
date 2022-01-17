package com.example.parkingmanagement.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingmanagement.R
import com.example.parkingmanagement.domain.model.ParkingSpaceData
import com.example.parkingmanagement.presentation.helper.extensions.observeLiveData
import com.example.parkingmanagement.presentation.helper.extensions.visibleOrGoneBasedOnCondition
import com.example.parkingmanagement.presentation.ui.allparking.AllParkingActivity
import com.example.parkingmanagement.presentation.ui.allreservation.AllReservationActivity
import com.example.parkingmanagement.presentation.ui.entrance.EntranceActivity
import com.example.parkingmanagement.presentation.ui.transactions.AllTransactionsActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.uiProgressIndicator
import kotlinx.android.synthetic.main.activity_home.uiToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity() {

    private val viewModel : HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(uiToolbar)
        setupUi()
        setListeners()
    }

    private fun setupUi() {
        viewModel.error.observeLiveData(this, ::showError)
        viewModel.loading.observeLiveData(this, ::showLoading)
        viewModel.parkingSpaceData.observeLiveData(this, ::showParkingSpaceData)
    }

    private fun setListeners() {
        uiToolbar.setNavigationOnClickListener { moveToEntranceScreen() }
        uiBtnAllParking.setOnClickListener { moveToAllParkingScreen() }
        uiBtnAllReservation.setOnClickListener { moveToAllReservationScreen() }
        uiTvRefresh.setOnClickListener { viewModel.refresh() }
        uiBtnAllTransactions.setOnClickListener { moveToAllTransactionsScreen() }
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(show: Boolean) {
        uiProgressIndicator.visibleOrGoneBasedOnCondition { show }
    }

    private fun showParkingSpaceData(parkingSpaceData: ParkingSpaceData) {
        with(parkingSpaceData) {
            uiTvNumberOfFloors.text = totalFloor.toString()
            uiTvNumberOfSpace.text = noOfParkingSpaceAllottedPerFloor.toString()
            uiTvAllotmentDetails.text = parkingSpaceAllotmentDetail
            uiTvAvailableParkingSpacesForCar.text = totalNoOfEmptyParkingSpacesAvailableForCar.toString()
            uiTvAvailableParkingSpacesForBike.text = totalNoOfEmptyParkingSpacesAvailableForBike.toString()
            uiTvAvailableParkingSpacesForBus.text = totalNoOfEmptyParkingSpacesAvailableForBus.toString()
        }
    }

    private fun moveToEntranceScreen() {
        startActivity(Intent(this, EntranceActivity::class.java))
    }

    private fun moveToAllParkingScreen() {
        startActivity(Intent(this, AllParkingActivity::class.java))
    }

    private fun moveToAllReservationScreen() {
        startActivity(Intent(this, AllReservationActivity::class.java))
    }

    private fun moveToAllTransactionsScreen() {
        startActivity(Intent(this, AllTransactionsActivity::class.java))
    }
}
