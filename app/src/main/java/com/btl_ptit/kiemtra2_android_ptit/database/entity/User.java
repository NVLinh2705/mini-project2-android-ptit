package com.btl_ptit.kiemtra2_android_ptit.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "User",
    indices = {
        @Index(value = {"username"}, unique = true)
    }
)
public class User {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "username", defaultValue = "")
    private String username;

    @ColumnInfo(name = "password", defaultValue = "")
    private String password;

    @Ignore
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {}

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
