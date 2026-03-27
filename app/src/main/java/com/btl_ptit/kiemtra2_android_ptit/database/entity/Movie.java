package com.btl_ptit.kiemtra2_android_ptit.database.entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey(autoGenerate = true) public int id;
    public String title;
    public String description;
    public String imageUrl;


    public Movie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
