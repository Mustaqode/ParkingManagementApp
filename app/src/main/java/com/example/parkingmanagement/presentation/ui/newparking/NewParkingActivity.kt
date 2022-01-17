package com.example.parkingmanagement.presentation.ui.newparking

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingmanagement.R
import com.example.parkingmanagement.presentation.helper.Trigger
import com.example.parkingmanagement.presentation.helper.extensions.getString
import com.example.parkingmanagement.presentation.helper.extensions.observeLiveData
import com.example.parkingmanagement.presentation.helper.extensions.visibleOrGoneBasedOnCondition
import com.example.parkingmanagement.presentation.ui.allparking.AllParkingActivity
import kotlinx.android.synthetic.main.activity_new_parking.*
import kotlinx.android.synthetic.main.activity_new_parking.uiProgressIndicator
import kotlinx.android.synthetic.main.activity_new_parking.uiToolbar
import kotlinx.android.synthetic.main.activity_new_parking.uiTvCouponDetails
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewParkingActivity : AppCompatActivity() {

    private val viewModel: NewParkingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_parking)
        setSupportActionBar(uiToolbar)
        setupUi()
        setListeners()
    }

    private fun setupUi() {
        viewModel.error.observeLiveData(this, ::showError)
        viewModel.loading.observeLiveData(this, ::showLoading)
        viewModel.couponDetails.observeLiveData(this, ::showCouponDetails)
        viewModel.park.observeLiveData(this, ::moveToAllParkingScreen)
    }

    private fun setListeners() {
        uiToolbar.setNavigationOnClickListener { moveToAllParkingScreen(Trigger) }
        uiBtnCreateANewParking.setOnClickListener {
            viewModel.park(
                uiEtVehicleNumber.getString(),
                uiSpinnerVehicleType.selectedItem.toString()
            )
        }
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(show: Boolean) {
        uiProgressIndicator.visibleOrGoneBasedOnCondition { show }
    }

    private fun showCouponDetails(couponDetail: String) {
        uiTvCouponDetails.text = couponDetail
    }

    private fun moveToAllParkingScreen(trigger: Trigger) {
        startActivity(Intent(this, AllParkingActivity::class.java))
    }

}

