package com.btl_ptit.kiemtra2_android_ptit.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.btl_ptit.kiemtra2_android_ptit.database.entity.Theater;

import java.util.List;
@Dao
public interface TheaterDAO {
    @Insert
    void insertTheater(Theater theater);
    @Query("SELECT * FROM theaters")
    List<Theater> getAllTheaters();
}
