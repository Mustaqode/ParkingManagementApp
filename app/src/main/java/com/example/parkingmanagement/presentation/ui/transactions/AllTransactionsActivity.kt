package com.example.parkingmanagement.presentation.ui.transactions

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parkingmanagement.R
import com.example.parkingmanagement.domain.model.TransactionSummary
import com.example.parkingmanagement.presentation.helper.Trigger
import com.example.parkingmanagement.presentation.helper.extensions.gone
import com.example.parkingmanagement.presentation.helper.extensions.observeLiveData
import com.example.parkingmanagement.presentation.helper.extensions.visible
import com.example.parkingmanagement.presentation.helper.extensions.visibleOrGoneBasedOnCondition
import com.example.parkingmanagement.presentation.ui.transactions.adapter.AllTransactionsAdapter
import kotlinx.android.synthetic.main.activity_all_transactions.*
import kotlinx.android.synthetic.main.activity_all_transactions.uiProgressIndicator
import kotlinx.android.synthetic.main.activity_all_transactions.uiToolbar
import kotlinx.android.synthetic.main.activity_all_transactions.uiTvNoDataFound
import org.koin.androidx.viewmodel.ext.android.viewModel


class AllTransactionsActivity : AppCompatActivity() {

    private val viewModel : AllTransactionsViewModel by viewModel()
    private val adapter by lazy { AllTransactionsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_transactions)
        setSupportActionBar(uiToolbar)
        setupUi()
        setListeners()
    }

    private fun setupUi() {
        setupRecyclerview()
        viewModel.error.observeLiveData(this, ::showError)
        viewModel.loading.observeLiveData(this, ::showLoading)
        viewModel.noData.observeLiveData(this, ::showNoData)
        viewModel.allTransactions.observeLiveData(this, ::showAllTransactions)
    }

    private fun setListeners() {
        uiToolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupRecyclerview() {
        uiRvAllTransactions.apply {
            layoutManager = LinearLayoutManager(this@AllTransactionsActivity)
            adapter = this@AllTransactionsActivity.adapter
        }
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(show: Boolean) {
        uiProgressIndicator.visibleOrGoneBasedOnCondition { show }
    }

    private fun showNoData(trigger: Trigger) {
        uiTvNoDataFound.visible()
    }

    private fun showAllTransactions(allTransactions: List<TransactionSummary>) {
        uiTvNoDataFound.gone()
        adapter.setData(allTransactions)
    }
}
