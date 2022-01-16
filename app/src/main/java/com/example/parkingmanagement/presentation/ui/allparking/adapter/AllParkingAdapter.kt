package com.example.parkingmanagement.presentation.ui.allparking.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkingmanagement.R
import com.example.parkingmanagement.domain.model.ParkingData
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.model_parking_details.view.*


class AllParkingAdapter(private val onDepartClicked: (ParkingData) -> Unit) :
    RecyclerView.Adapter<AllParkingAdapter.AllParkingViewHolder>() {

    private val allParking = arrayListOf<ParkingData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllParkingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.model_parking_details, parent)
        return AllParkingViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllParkingViewHolder, position: Int) {
        val parking = allParking[position]
        with(holder) {
            vehicleNumber.text = parking.vehicleNumber
            vehicleType.text = parking.vehicleType
            floorNumber.text = parking.floorNumber.toString()
            offerCouponApplied.text = parking.offerCouponApplied
        }
    }

    override fun getItemCount(): Int = allParking.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(allParkingList: List<ParkingData>) {
        allParking.clear()
        allParking.addAll(allParkingList)
        notifyDataSetChanged()
    }

    inner class AllParkingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vehicleNumber : AppCompatTextView = view.uiTvVehicleNumber
        val vehicleType : AppCompatTextView = view.uiTvVehicleType
        val floorNumber : AppCompatTextView = view.uiTvFloorNumber
        val offerCouponApplied : AppCompatTextView = view.uiTvOfferCouponApplied
        private val departButton : MaterialButton = view.uiBtnDepart

        init {
            departButton.setOnClickListener {
                onDepartClicked.invoke(allParking[adapterPosition])
            }
        }
    }
}
