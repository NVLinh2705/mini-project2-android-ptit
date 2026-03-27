package com.btl_ptit.kiemtra2_android_ptit.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.btl_ptit.kiemtra2_android_ptit.database.dao.TheaterDAO;
import com.btl_ptit.kiemtra2_android_ptit.database.dao.UserDAO;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Theater;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.User;
import com.btl_ptit.kiemtra2_android_ptit.utils.Constants;
import com.btl_ptit.kiemtra2_android_ptit.utils.Converters;

@Database(entities = {User.class, Theater.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract TheaterDAO theaterDAO();
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            Constants.databaseName
                    )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
