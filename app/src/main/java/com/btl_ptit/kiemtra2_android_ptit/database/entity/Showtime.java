package com.btl_ptit.kiemtra2_android_ptit.database.entity;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "showtimes", foreignKeys = {
    @ForeignKey(entity = Movie.class, parentColumns = "id", childColumns = "movieId", onDelete = ForeignKey.CASCADE),
    @ForeignKey(entity = Theater.class, parentColumns = "id", childColumns = "theaterId", onDelete = ForeignKey.CASCADE)
})
public class Showtime {
    @PrimaryKey(autoGenerate = true) public int id;
    public int movieId;
    public int theaterId;
    public LocalDateTime startTime;

    public LocalDateTime endTime;

    public int seatNum;

}
