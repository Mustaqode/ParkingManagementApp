package com.example.parkingmanagement.presentation.ui.allparking

 import android.content.Intent
 import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
 import androidx.recyclerview.widget.LinearLayoutManager
 import com.example.parkingmanagement.R
 import com.example.parkingmanagement.domain.model.ParkingData
 import com.example.parkingmanagement.presentation.helper.Trigger
 import com.example.parkingmanagement.presentation.helper.extensions.gone
 import com.example.parkingmanagement.presentation.helper.extensions.observeLiveData
 import com.example.parkingmanagement.presentation.helper.extensions.visible
 import com.example.parkingmanagement.presentation.helper.extensions.visibleOrGoneBasedOnCondition
 import com.example.parkingmanagement.presentation.ui.allparking.adapter.AllParkingAdapter
 import com.example.parkingmanagement.presentation.ui.newparking.NewParkingActivity
 import com.example.parkingmanagement.presentation.ui.transactions.AllTransactionsActivity
 import kotlinx.android.synthetic.main.activity_all_parking.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class AllParkingActivity : AppCompatActivity() {

    private val viewModel : AllParkingViewModel by viewModel()
    private val adapter : AllParkingAdapter by lazy { AllParkingAdapter(::onDepartClicked) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_parking)
        setupUi()
        setListeners()
    }

    private fun setupUi() {
        setupRecyclerViewAdapter()
        viewModel.error.observeLiveData(this, ::showError)
        viewModel.loading.observeLiveData(this, ::showLoading)
        viewModel.noData.observeLiveData(this, ::showNoData)
        viewModel.depart.observeLiveData(this, ::removeItemAndMoveToTransactionsScreen)
        viewModel.allParking.observeLiveData(this, ::showAllParking)
    }

    private fun setListeners() {
        uiToolbar.setNavigationOnClickListener { onBackPressed() }
        uiBtnAddANewParking.setOnClickListener { moveToNewParkingScreen() }
    }

    private fun setupRecyclerViewAdapter() {
        uiRvAllParking.apply {
            layoutManager = LinearLayoutManager(this@AllParkingActivity)
            adapter = this@AllParkingActivity.adapter
        }
    }


    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(show: Boolean) {
        uiProgressIndicator.visibleOrGoneBasedOnCondition { show }
    }

    private fun onDepartClicked(parkingData: ParkingData, index: Int) {
        viewModel.depart(parkingData, index)
    }

    private fun showNoData(trigger: Trigger) {
        uiTvNoDataFound.visible()
    }

    private fun showAllParking(allParkingList: List<ParkingData>) {
        uiTvNoDataFound.gone()
        adapter.setData(allParkingList)
    }

    private fun removeItemAndMoveToTransactionsScreen(index: Int) {
        adapter.removeParkingItem(index)
        startActivity(Intent(this, AllTransactionsActivity::class.java))
    }


    private fun moveToNewParkingScreen() {
        startActivity(Intent(this, NewParkingActivity::class.java))
    }

}
