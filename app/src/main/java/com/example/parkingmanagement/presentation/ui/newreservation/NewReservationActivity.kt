package com.example.parkingmanagement.presentation.ui.newreservation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingmanagement.R
import com.example.parkingmanagement.presentation.helper.Trigger
import com.example.parkingmanagement.presentation.helper.extensions.getString
import com.example.parkingmanagement.presentation.helper.extensions.observeLiveData
import com.example.parkingmanagement.presentation.helper.extensions.visibleOrGoneBasedOnCondition
import com.example.parkingmanagement.presentation.ui.allreservation.AllReservationActivity
import kotlinx.android.synthetic.main.activity_new_reservation.*
import kotlinx.android.synthetic.main.activity_new_reservation.uiProgressIndicator
import kotlinx.android.synthetic.main.activity_new_reservation.uiToolbar
import kotlinx.android.synthetic.main.activity_new_reservation.uiTvCouponDetails
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewReservationActivity : AppCompatActivity() {

    private val viewModel: NewReservationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_reservation)
        setSupportActionBar(uiToolbar)
        setupUi()
        setListeners()
    }

    private fun setupUi() {
        viewModel.error.observeLiveData(this, ::showError)
        viewModel.loading.observeLiveData(this, ::showLoading)
        viewModel.couponDetails.observeLiveData(this, ::showCouponDetails)
        viewModel.reserve.observeLiveData(this, ::moveToAllReservationsScreen)
    }

    private fun setListeners() {
        uiToolbar.setNavigationOnClickListener { moveToAllReservationsScreen(Trigger) }
        uiBtnCreateANewReservation.setOnClickListener {
            viewModel.reserve(
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

    private fun moveToAllReservationsScreen(trigger: Trigger) {
        startActivity(Intent(this, AllReservationActivity::class.java))
    }
}
