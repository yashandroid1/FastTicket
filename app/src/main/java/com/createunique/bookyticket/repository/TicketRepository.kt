package com.createunique.bookyticket.repository

import com.createunique.bookyticket.modals.TicketDao
import com.createunique.bookyticket.modals.TicketModel
import javax.inject.Inject

class TicketRepository @Inject constructor(private val ticketDao: TicketDao) {

    suspend fun insertTicket(ticketModel: TicketModel){
        ticketDao.insertTicket(ticketModel)
    }


    suspend fun getTicketList():List<TicketModel>{
        return ticketDao.getTickets()
    }


    suspend fun deleteticket(id:Int){
        ticketDao.deleteTicketByID(id)
    }


    suspend fun updateticketbyId(id:Int , name: String, reason: String, priority: String , date:String){
        ticketDao.updateTicketByID(id , name, reason, priority, date)
    }

    suspend fun getTicketbyID(id: Int): TicketModel? {
        return ticketDao.getTicketByID(id)
    }

}