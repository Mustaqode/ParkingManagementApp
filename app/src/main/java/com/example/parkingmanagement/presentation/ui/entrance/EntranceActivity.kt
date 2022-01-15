package com.example.parkingmanagement.presentation.ui.entrance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.parkingmanagement.R
import com.example.parkingmanagement.presentation.helper.Trigger
import com.example.parkingmanagement.presentation.helper.extensions.*
import com.example.parkingmanagement.presentation.ui.home.HomeActivity
import com.example.parkingmanagement.presentation.ui.newparking.NewParkingSpaceActivity
import kotlinx.android.synthetic.main.activity_entrance.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EntranceActivity : AppCompatActivity() {

    private val viewModel: EntranceViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrance)
        setupUi()
        setListeners()
    }

    private fun setupUi() {
        viewModel.error.observeLiveData(this, ::showError)
        viewModel.loading.observeLiveData(this, ::showLoading)
        viewModel.existingParkingSpace.observeLiveData(this, ::showExistingParkingSpaceUi)
        viewModel.newParkingSpace.observeLiveData(this, ::showNewParkingSpaceUi)
        viewModel.resetAll.observeLiveData(this, ::showNewParkingSpaceUi)
    }

    private fun setListeners() {
        uiBtnNewParkingSpace.setOnClickListener { moveToNewParkingSpaceScreen() }
        uiBtnEnterParkingSpace.setOnClickListener { moveToHomeScreen() }
        uiTvReset.setOnClickListener { showResetDialog() }
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(show: Boolean) {
        uiProgressIndicator.visibleOrGoneBasedOnCondition { show }
    }

    private fun showExistingParkingSpaceUi(trigger: Trigger) {
        uiBtnNewParkingSpace.gone()
        uiBtnEnterParkingSpace.visible()
        uiTvReset.visible()
    }

    private fun showNewParkingSpaceUi(trigger: Trigger) {
        uiBtnNewParkingSpace.visible()
        uiBtnEnterParkingSpace.gone()
        uiTvReset.gone()
    }

    private fun moveToHomeScreen() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun moveToNewParkingSpaceScreen() {
        startActivity(Intent(this, NewParkingSpaceActivity::class.java))
    }

    private fun showResetDialog() {
        showAlertDialog("Reset all data", "Are you sure about resetting all data?", positive = {
            viewModel.resetAllData()
        })
    }

}
