package com.createunique.bookyticket.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.createunique.bookyticket.TicketsDetails
import com.createunique.bookyticket.databinding.TicketBinding
import com.createunique.bookyticket.modals.TicketModel

class TicketAdapter(
    private val context: Context,
    private val onDeleteClickListener : (model: TicketModel,position:Int) -> Unit
) : RecyclerView.Adapter<TicketAdapter.ItemViewHolder>() {

    private var ticketList = mutableListOf<TicketModel>()


    inner class ItemViewHolder(private val binding: TicketBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: TicketModel) {
            binding.apply {
                model.let { ticketData ->
                    nameTxt.text = ticketData.name
                    priorityTxt.text = ticketData.priority
                    cancelBtn.setOnClickListener {
                        onDeleteClickListener(model,adapterPosition)
                    }

                }
                binding.root.setOnClickListener {

                    val intent = Intent(context, TicketsDetails::class.java)
                    intent.putExtra("id", model.id)  // Pass the ticket ID to the next activity
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = TicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = ticketList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return ticketList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<TicketModel>) {
        ticketList.clear()
        ticketList.addAll(newList)
        notifyDataSetChanged()
    }

}
