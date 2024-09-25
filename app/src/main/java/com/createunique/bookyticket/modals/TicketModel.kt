package com.createunique.bookyticket.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ticket_tbl")
data class TicketModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int ,
    val name:String ,
    val reason:String ,
    val priority:String ,
    val ticketid:String ,
    val date:String
)

