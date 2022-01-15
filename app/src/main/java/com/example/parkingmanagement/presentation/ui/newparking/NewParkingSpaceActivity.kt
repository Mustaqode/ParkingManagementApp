package com.example.parkingmanagement.presentation.ui.newparking

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parkingmanagement.R
import com.example.parkingmanagement.presentation.helper.Trigger
import com.example.parkingmanagement.presentation.helper.extensions.*
import com.example.parkingmanagement.presentation.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_entrance.uiProgressIndicator
import kotlinx.android.synthetic.main.activity_new_parking_space.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewParkingSpaceActivity : AppCompatActivity() {

    private val spaceViewModel: NewParkingSpaceViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_parking_space)
        setSupportActionBar(uiToolbar)
        setupUi()
        setListeners()
    }

    private fun setupUi() {
        spaceViewModel.error.observeLiveData(this, ::showError)
        spaceViewModel.loading.observeLiveData(this, ::showLoading)
        spaceViewModel.couponDetails.observeLiveData(this, ::showCouponDetails)
        spaceViewModel.parkingSpaceCreation.observeLiveData(this, ::moveToHomeScreen)
    }

    private fun setListeners() {
        uiToolbar.setNavigationOnClickListener { onBackPressed() }
        uiBtnCreateANewSpace.setOnClickListener {
            spaceViewModel.createSpace(uiEtNumberOfFloors.text.getInt(), uiEtNumberOfSpace.text.getInt())
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

    private fun moveToHomeScreen(trigger: Trigger) {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}
