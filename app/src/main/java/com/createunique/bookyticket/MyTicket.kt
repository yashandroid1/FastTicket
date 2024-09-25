package com.createunique.bookyticket

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.createunique.bookyticket.databinding.ActivityMyTicketBinding

class MyTicket : AppCompatActivity() {
    private lateinit var binding:ActivityMyTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}