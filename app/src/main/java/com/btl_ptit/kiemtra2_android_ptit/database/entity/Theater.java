package com.btl_ptit.kiemtra2_android_ptit.database.entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "theaters")
public class Theater {
    @PrimaryKey(autoGenerate = true) public int id;
    public String name;
    public String address;
}
