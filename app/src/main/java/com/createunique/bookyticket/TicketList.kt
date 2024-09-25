package com.createunique.bookyticket

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.createunique.bookyticket.adapter.TicketAdapter
import com.createunique.bookyticket.databinding.ActivityTicketListBinding
import com.createunique.bookyticket.modals.TicketModel
import com.createunique.bookyticket.viewmodels.TicketViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TicketList : AppCompatActivity() {
    private lateinit var binding:ActivityTicketListBinding
    private lateinit var ticketAdapter: TicketAdapter
    private val ticketViewModel:TicketViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStatusBarGradient()

        ticketAdapter = TicketAdapter(this) { data, position ->
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete Ticket")
            builder.setMessage("Are you sure you want to delete this ticket?")

            builder.setPositiveButton("Yes") { dialog, _ ->
                ticketViewModel.deleteTicketById(data.id)
                dialog.dismiss()
            }

            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

            builder.show()
        }

        binding.rvLIst.apply {
            layoutManager = LinearLayoutManager(this@TicketList)
            setHasFixedSize(true)
            adapter = ticketAdapter
        }

        ticketViewModel.getTickets()

        ticketViewModel.ticketList.observe(this , Observer {

            Log.d("YASHANDROID" , it.toString())

            if (it.isEmpty()) {
                binding.noImg.visibility = View.VISIBLE
                binding.noTxt.visibility = View.VISIBLE
            }
            else{
                ticketAdapter.updateList(it)
            }



        })
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

}