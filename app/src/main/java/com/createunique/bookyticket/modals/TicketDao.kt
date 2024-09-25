package com.createunique.bookyticket.modals

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface TicketDao {

        @Insert
        suspend fun insertTicket(ticketModel: TicketModel)

        @Query("SELECT * FROM ticket_tbl")
        suspend fun getTickets(): List<TicketModel>

        @Query("DELETE FROM ticket_tbl WHERE id = :id")
        suspend fun deleteTicketByID(id: Int)

        @Update
        suspend fun updateTicket(ticketModel: TicketModel)

        // Update multiple fields by ID, including date
        @Query("UPDATE ticket_tbl SET name = :name, reason = :reason, priority = :priority, date = :date WHERE id = :id")
        suspend fun updateTicketByID(id: Int, name: String, reason: String, priority: String, date: String)

        // Get a single ticket by ID
        @Query("SELECT * FROM ticket_tbl WHERE id = :id")
        suspend fun getTicketByID(id: Int): TicketModel?
}
