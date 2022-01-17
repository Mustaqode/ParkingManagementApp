package com.example.parkingmanagement.presentation.ui.transactions.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkingmanagement.R
import com.example.parkingmanagement.domain.model.TransactionSummary
import kotlinx.android.synthetic.main.model_transaction_summary.view.*


class AllTransactionsAdapter : RecyclerView.Adapter<AllTransactionsAdapter.AllTransactionsViewHolder>() {

    private val allTransactions = arrayListOf<TransactionSummary>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTransactionsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.model_transaction_summary, parent, false)
        return AllTransactionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllTransactionsViewHolder, position: Int) {
        val transaction = allTransactions[position]
        with(holder) {
            transactionId.text = transaction.transactionId
            vehicleNumber.text = transaction.vehicleNumber
            vehicleType.text = transaction.vehicleType
            floorNumber.text = transaction.floorNumber.toString()
            offerCouponApplied.text = transaction.isCouponApplied
            noOfHours.text = transaction.noOfHours
            totalCost.text = transaction.totalCost
        }
    }

    override fun getItemCount(): Int = allTransactions.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(allTransactionsList: List<TransactionSummary>) {
        allTransactions.clear()
        allTransactions.addAll(allTransactionsList)
        notifyDataSetChanged()
    }

    inner class AllTransactionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val transactionId: AppCompatTextView = view.uiTvTransactionId
        val vehicleNumber : AppCompatTextView = view.uiTvVehicleNumber
        val vehicleType : AppCompatTextView = view.uiTvVehicleType
        val floorNumber : AppCompatTextView = view.uiTvFloorNumber
        val offerCouponApplied : AppCompatTextView = view.uiTvOfferCouponApplied
        val noOfHours: AppCompatTextView = view.uiTvNoOfHours
        val totalCost : AppCompatTextView = view.uiTvTotalCost
    }
}
