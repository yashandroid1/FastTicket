package com.createunique.bookyticket

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.createunique.bookyticket.databinding.ActivityTicketsDetailsBinding
import com.createunique.bookyticket.viewmodels.TicketViewModel
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class TicketsDetails : AppCompatActivity() {
    private lateinit var binding: ActivityTicketsDetailsBinding
    private val ticketViewModel:TicketViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", -1)

        if (id != -1) {

            ticketViewModel.getTicketByID(id)
            observeTicket(id)
            Log.d("TicketDetails", "Ticket ID: $id")

        } else {

            Log.d("TicketDetails", "No valid ticket ID passed")

        }

        binding.updateTkt.setOnClickListener {
            val intent = Intent(this, UpdateTicket::class.java)
            intent.putExtra("id", id)  // Pass the ticket ID to the next activity
            startActivity(intent)
        }


       setStatusBarGradient()

    }

    private fun setStatusBarGradient() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            val background: Drawable = ContextCompat.getDrawable(this, R.drawable.gradient_status)!!
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
            window.setBackgroundDrawable(background)
        }

    }

    private fun observeTicket(id:Int){
        // Observe the ticket LiveData
        ticketViewModel.ticket.observe(this) { ticket ->
            ticket?.let {
                binding.apply {
                    tktId.text = "Ticket -  ${it.ticketid}"
                    nameTxt.text = it.name
                    reasonTxt.text = it.reason
                    dateTxt.text = it.date
                    priorityTxt.text = it.priority


                    if (it.date=="null"){
                        dateTxt.visibility = View.GONE
                        priorityTxt.visibility = View.GONE
                        priorityyTxt.visibility = View.VISIBLE
                    }
                }
            } ?: run {
                Log.e("TicketsDetails", "No ticket found with ID: $id")
            }
        }
    }
}
