package com.example.parkingmanagement.presentation.ui.allreservation.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkingmanagement.R
import com.example.parkingmanagement.domain.model.ReservationData
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.model_reservation_setails.view.*


class AllReservationAdapter(private val onUnReserveClicked: (ReservationData, Int) -> Unit) :
    RecyclerView.Adapter<AllReservationAdapter.AllReservationViewHolder>() {

    private val allReservation = arrayListOf<ReservationData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllReservationViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.model_reservation_setails, parent, false)
        return AllReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllReservationViewHolder, position: Int) {
        val reservation = allReservation[position]
        with(holder) {
            vehicleNumber.text = reservation.vehicleNumber
            vehicleType.text = reservation.vehicleType
            floorNumber.text = reservation.floorNumber.toString()
            offerCouponApplied.text = reservation.offerCouponApplied
            noOfHours.text = reservation.noOfHours
        }
    }

    override fun getItemCount(): Int = allReservation.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(allReservationList: List<ReservationData>) {
        allReservation.clear()
        allReservation.addAll(allReservationList)
        notifyDataSetChanged()
    }

    fun removeReservationItem(index: Int) {
        allReservation.removeAt(index)
        notifyItemChanged(index)
    }

    inner class AllReservationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vehicleNumber : AppCompatTextView = view.uiTvVehicleNumber
        val vehicleType : AppCompatTextView = view.uiTvVehicleType
        val floorNumber : AppCompatTextView = view.uiTvFloorNumber
        val offerCouponApplied : AppCompatTextView = view.uiTvOfferCouponApplied
        val noOfHours: AppCompatTextView = view.uiTvNoOfHours
        private val unReserveButton : MaterialButton = view.uiBtnUnReserve

        init {
            unReserveButton.setOnClickListener {
                onUnReserveClicked.invoke(allReservation[adapterPosition], adapterPosition)
            }
        }
    }
}
