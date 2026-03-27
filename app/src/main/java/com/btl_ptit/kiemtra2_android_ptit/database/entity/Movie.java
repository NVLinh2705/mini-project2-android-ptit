package com.btl_ptit.kiemtra2_android_ptit.database.entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey(autoGenerate = true) public int id;
    public String title;
    public String description;
    public String imageUrl;
}
