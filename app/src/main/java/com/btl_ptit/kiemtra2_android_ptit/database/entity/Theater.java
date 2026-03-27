package com.btl_ptit.kiemtra2_android_ptit.database.entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "theaters")
public class Theater {
    @PrimaryKey(autoGenerate = true) public int id;
    public String name;
    public String address;

    public Theater(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Theater(){};
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
