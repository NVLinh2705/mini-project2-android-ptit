package com.btl_ptit.kiemtra2_android_ptit.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.btl_ptit.kiemtra2_android_ptit.database.entity.Ticket;
@Dao
public interface TicketDAO {
    @Insert
    void insertTicket(Ticket ticket);
    @Query("SELECT * FROM tickets WHERE id = :id")
    Ticket getTicket(int id);
}
