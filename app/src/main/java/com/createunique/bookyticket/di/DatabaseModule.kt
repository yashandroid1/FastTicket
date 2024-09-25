package com.createunique.bookyticket.di

import android.content.Context
import androidx.room.Room
import com.createunique.bookyticket.modals.TicketDao
import com.createunique.bookyticket.roomDb.TicketDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TicketDB {
        return Room.databaseBuilder(
            context,
            TicketDB::class.java,
            "ticket_database"
        ).build()
    }

    @Provides
    fun provideUserDao(database: TicketDB): TicketDao {
        return database.userDao()
    }
}
