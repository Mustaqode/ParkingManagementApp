package com.example.parkingmanagement.presentation.ui.allreservation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parkingmanagement.R
import com.example.parkingmanagement.domain.model.ReservationData
import com.example.parkingmanagement.presentation.helper.Trigger
import com.example.parkingmanagement.presentation.helper.extensions.gone
import com.example.parkingmanagement.presentation.helper.extensions.observeLiveData
import com.example.parkingmanagement.presentation.helper.extensions.visible
import com.example.parkingmanagement.presentation.helper.extensions.visibleOrGoneBasedOnCondition
import com.example.parkingmanagement.presentation.ui.allreservation.adapter.AllReservationAdapter
import com.example.parkingmanagement.presentation.ui.newreservation.NewReservationActivity
import kotlinx.android.synthetic.main.activity_all_reservation.*
import kotlinx.android.synthetic.main.activity_all_reservation.uiProgressIndicator
import kotlinx.android.synthetic.main.activity_all_reservation.uiToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class AllReservationActivity : AppCompatActivity() {

    private val viewModel: AllReservationViewModel by viewModel()
    private val adapter by lazy { AllReservationAdapter(::onUnReserveClicked) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_reservation)
        setupUi()
        setListeners()
    }

    private fun setupUi() {
        setupRecyclerViewAdapter()
        viewModel.error.observeLiveData(this, ::showError)
        viewModel.loading.observeLiveData(this, ::showLoading)
        viewModel.noData.observeLiveData(this, ::showNoData)
        viewModel.unReserve.observeLiveData(this, ::removeItemFromTheReservationList)
        viewModel.allReservation.observeLiveData(this, ::showAllReservation)
    }

    private fun setListeners() {
        uiToolbar.setNavigationOnClickListener { onBackPressed() }
        uiBtnAddANewReservation.setOnClickListener { moveToNewReservationScreen() }
    }

    private fun setupRecyclerViewAdapter() {
        uiRvAllReservation.apply {
            layoutManager = LinearLayoutManager(this@AllReservationActivity)
            adapter = this@AllReservationActivity.adapter
        }
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(show: Boolean) {
        uiProgressIndicator.visibleOrGoneBasedOnCondition { show }
    }

    private fun onUnReserveClicked(reservationData: ReservationData, index: Int) {
        viewModel.unReserve(reservationData, index)
    }

    private fun showNoData(trigger: Trigger) {
        uiTvNoDataFound.visible()
    }

    private fun showAllReservation(allReservationList: List<ReservationData>) {
        uiTvNoDataFound.gone()
        adapter.setData(allReservationList)
    }

    private fun removeItemFromTheReservationList(index: Int) {
        adapter.removeReservationItem(index)
    }

    private fun moveToNewReservationScreen() {
        startActivity(Intent(this, NewReservationActivity::class.java))
    }
}
