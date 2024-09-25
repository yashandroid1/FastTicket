package com.createunique.bookyticket

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.createunique.bookyticket.databinding.ActivityUpdateTicketBinding
import com.createunique.bookyticket.modals.TicketModel
import com.createunique.bookyticket.viewmodels.TicketViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale


@AndroidEntryPoint
class UpdateTicket : AppCompatActivity() {
    private lateinit var binding:ActivityUpdateTicketBinding
    private val ticketViewModel: TicketViewModel by viewModels()
    private val calendar = Calendar.getInstance()
    private lateinit var runnable:Runnable
    private var sltPriority = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", -1)

        if (id != -1) {

            ticketViewModel.getTicketByID(id)
            observeTicket(id)
            Log.d("TicketDetails", "Ticket ID: $id")

        } else {

            Log.d("TicketDetails", "No valid ticket ID passed")

        }

        setPriority()
        setStatusBarGradient()

        ticketViewModel.getTicketByID(id)

        binding.dateTxt.setOnClickListener {
            datePicker()
        }


        binding.apply {

            updateBtn.setOnClickListener {
                if (nameTxt.text.isNotEmpty() && reasonTxt.text.isNotEmpty() ) {

                    if (dateTxt.text.toString() != "Select Date   (Optional)"){

//                        val ticket = TicketModel(0 , nameTxt.text.toString() , reasonTxt.text.toString() ,
//                            sltPriority , "879897" , dateTxt.text.toString()
//                        )

                        ticketViewModel.updateTicketById( id , nameTxt.text.toString() ,
                            reasonTxt.text.toString() , sltPriority , dateTxt.text.toString() )
                        Toast.makeText(this@UpdateTicket, "Update Successfully", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@UpdateTicket , Home::class.java))
                    }

                    else{
//                        val ticket = TicketModel(0 , nameTxt.text.toString() , reasonTxt.text.toString() ,
//                            sltPriority , "87587" , "null"
//                        )

                        ticketViewModel.updateTicketById( 3 , nameTxt.text.toString() ,
                            reasonTxt.text.toString() , sltPriority , dateTxt.text.toString() )
                        Toast.makeText(this@UpdateTicket, "Update Successfully", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@UpdateTicket , Home::class.java))
                    }




                } else {
                    Toast.makeText(this@UpdateTicket, "Please fill all required fields correctly", Toast.LENGTH_SHORT).show()
                }
            }

        }


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


    private fun datePicker() {

        val datePickerDialogue = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, monthOfYear, dayOfMonth)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)
            binding.dateTxt.text = formattedDate
        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH))
        datePickerDialogue.show()
    }

    private fun setPriority() {
        val spinner2: Spinner = binding.priorty
        val priorityList: List<String> = listOf("Low", "High", "Medium")

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorityList)
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner2.adapter = adapter2


        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                sltPriority = priorityList.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun observeTicket(id:Int){
        ticketViewModel.ticket.observe(this) { ticket ->
            ticket?.let {
                binding.apply {
                    nameTxt.setText(it.name)
                    reasonTxt.setText(it.reason)
                    if (dateTxt.text!="null"){
                        dateTxt.visibility = View.VISIBLE
                        dateTxt.text = it.date
                    }
                }
            } ?: run {
                Log.e("TicketsDetails", "No ticket found with ID: $id")
            }
        }
    }
}