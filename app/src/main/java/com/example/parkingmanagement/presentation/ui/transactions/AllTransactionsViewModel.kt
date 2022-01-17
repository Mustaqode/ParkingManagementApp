package com.example.parkingmanagement.presentation.ui.transactions

import androidx.lifecycle.MutableLiveData
import com.example.parkingmanagement.domain.model.TransactionSummary
import com.example.parkingmanagement.domain.use_case.alltransactions.GetAllTransactionsUseCase
import com.example.parkingmanagement.presentation.helper.Trigger
import com.example.parkingmanagement.presentation.helper.extensions.trigger
import com.example.parkingmanagement.presentation.ui.base.BaseViewModel


class AllTransactionsViewModel(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase
) : BaseViewModel() {

    private val allTransactionsLd = MutableLiveData<List<TransactionSummary>>()
    private val noDataLd = MutableLiveData<Trigger>()

    init {
        getAllTransactionsUseCase().processResult {
            if (this.isNullOrEmpty()) {
                noDataLd.trigger()
            } else
                allTransactionsLd.value = this
        }
    }

    val allTransactions = allTransactionsLd
    val noData = noDataLd
}
