package com.createunique.bookyticket.roomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.createunique.bookyticket.modals.TicketDao
import com.createunique.bookyticket.modals.TicketModel



@Database(entities = [TicketModel::class], version = 1, exportSchema = false)
abstract class TicketDB : RoomDatabase() {
    abstract fun userDao(): TicketDao

    companion object {
        @Volatile
        private var INSTANCE: TicketDB? = null

        fun getDatabase(context: Context): TicketDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TicketDB::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}