package com.btl_ptit.kiemtra2_android_ptit.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.btl_ptit.kiemtra2_android_ptit.database.entity.Showtime;

import java.util.List;
@Dao
public interface ShowtimeDAO {
    @Insert
    void insertShowtime(Showtime showtime);
    @Query("SELECT * FROM showtimes WHERE movieId = :movieId")
    List<Showtime> getShowtimesByMovie(int movieId);
    @Query("SELECT * FROM showtimes")
    List<Showtime> getAllShowtimes();
    @Query("SELECT * FROM showtimes WHERE id = :id")
    Showtime getShowtimeById(int id);
}
