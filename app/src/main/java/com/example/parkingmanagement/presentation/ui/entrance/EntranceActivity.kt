package com.example.parkingmanagement.presentation.ui.entrance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.parkingmanagement.R
import com.example.parkingmanagement.presentation.helper.Trigger
import com.example.parkingmanagement.presentation.helper.extensions.gone
import com.example.parkingmanagement.presentation.helper.extensions.observeLiveData
import com.example.parkingmanagement.presentation.helper.extensions.showAlertDialog
import com.example.parkingmanagement.presentation.helper.extensions.visible
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
        uiBtnNewParkingSpace.setOnClickListener {

        }
        uiBtnEnterParkingSpace.setOnClickListener {

        }
        uiTvReset.setOnClickListener {
            showAlertDialog("Reset all data", "Are you sure about resetting all data?", positive = {
                viewModel.resetAllData()
            })
        }

    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(trigger: Trigger) {
        uiProgressIndicator.visible()
    }

    private fun showExistingParkingSpaceUi(trigger: Trigger) {
        uiProgressIndicator.gone()
        uiBtnNewParkingSpace.gone()
        uiBtnEnterParkingSpace.visible()
        uiTvReset.visible()
    }

    private fun showNewParkingSpaceUi(trigger: Trigger) {
        uiProgressIndicator.gone()
        uiBtnNewParkingSpace.visible()
        uiBtnEnterParkingSpace.gone()
        uiTvReset.gone()
    }

}
