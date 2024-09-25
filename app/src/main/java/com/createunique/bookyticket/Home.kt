package com.createunique.bookyticket

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.util.Calendar
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.createunique.bookyticket.databinding.ActivityHomeBinding
import com.createunique.bookyticket.modals.TicketModel
import com.createunique.bookyticket.viewmodels.TicketViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.random.Random


@AndroidEntryPoint
class Home : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    private val ticketViewModel:TicketViewModel by viewModels()
    private val calendar = Calendar.getInstance()
    private lateinit var runnable:Runnable
    private val handler = Handler(Looper.getMainLooper())
    private var sltPriority = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setPriority()

        binding.dateTxt.setOnClickListener {
            datePicker()
        }

        // Observe the ticket list LiveData
        ticketViewModel.ticketList.observe(this) { tickets ->
            // Update the UI with the ticket list
            tickets?.let {

            }
        }

        // Fetch the ticket list when the fragment is created
        ticketViewModel.getTickets()



        binding.apply {

            bookBtn.setOnClickListener {

                val ticketid = Random.nextInt(100000, 999999)

                if (nameTxt.text.isNotEmpty() && reasonTxt.text.isNotEmpty() ) {

                    if (dateTxt.text.toString() != "Select Date   (Optional)"){

                        val ticket = TicketModel(0 , nameTxt.text.toString() , reasonTxt.text.toString() ,
                            sltPriority , ticketid.toString() , dateTxt.text.toString()
                        )

                        ticketViewModel.insertTicket(ticket)
                        inserted()

                    }

                    else{
                        val ticket = TicketModel(0 , nameTxt.text.toString() , reasonTxt.text.toString() ,
                            sltPriority , ticketid.toString() , "null"
                        )

                        ticketViewModel.insertTicket(ticket)
                        inserted()
                    }




                } else {
                    Toast.makeText(this@Home, "Please fill all required fields correctly", Toast.LENGTH_SHORT).show()
                }
            }

        }


        binding.mtTktBtn.setOnClickListener {
            val intent = Intent(this , TicketList::class.java)
            startActivity(intent)
        }

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

    private fun inserted(){
        binding.apply {
            nameTxt.setText("")
            reasonTxt.setText("")
            dateTxt.setText("Select Date   (Optional)")
            showDialogue()
        }
    }

    private fun showDialogue(){
        val dialogue = Dialog(this)
        dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogue.setCancelable(false)
        dialogue.setContentView(R.layout.success_layout)
        dialogue.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogue.show()

        runnable = Runnable {
            dialogue.dismiss()
        }

        handler.postDelayed(runnable , 1200)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

}