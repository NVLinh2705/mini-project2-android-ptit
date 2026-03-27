package com.btl_ptit.kiemtra2_android_ptit.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.btl_ptit.kiemtra2_android_ptit.database.entity.User;

import java.util.List;


@Dao
public interface UserDAO {
    @Query("SELECT * FROM User ORDER BY id DESC")
    List<User> getAllUsers();

    @Query("SELECT * FROM User WHERE id = :id")
    User getUserById(int id);

    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    User getUser(String username, String password);

    @Query("SELECT * FROM User")

    @Insert(onConflict = OnConflictStrategy.ABORT)
    Long insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

}
