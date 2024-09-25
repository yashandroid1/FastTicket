package com.createunique.bookyticket.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.createunique.bookyticket.modals.TicketModel
import com.createunique.bookyticket.repository.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
) : ViewModel() {

    private val _ticketList = MutableLiveData<List<TicketModel>>()
    val ticketList: LiveData<List<TicketModel>> get() = _ticketList

    private val _ticket = MutableLiveData<TicketModel?>()
    val ticket: LiveData<TicketModel?> get() = _ticket

    fun insertTicket(ticket: TicketModel) {
        viewModelScope.launch {
            ticketRepository.insertTicket(ticket)
            getTickets()
        }
    }

    fun getTickets() {
        viewModelScope.launch {
            val tickets = ticketRepository.getTicketList()
            _ticketList.postValue(tickets)
        }
    }

    fun deleteTicketById(id: Int) {
        viewModelScope.launch {
            ticketRepository.deleteticket(id)
            getTickets()
        }
    }

    fun updateTicketById(id: Int, name: String, reason: String, priority: String, date: String) {
        viewModelScope.launch {
            ticketRepository.updateticketbyId(id, name, reason, priority, date)
            getTickets()
        }
    }

    fun getTicketByID(id: Int) {
        viewModelScope.launch {
            val ticketData = ticketRepository.getTicketbyID(id)
            _ticket.postValue(ticketData)
        }
    }
}
