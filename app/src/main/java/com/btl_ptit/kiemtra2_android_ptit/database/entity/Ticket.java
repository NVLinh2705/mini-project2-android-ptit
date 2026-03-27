package com.btl_ptit.kiemtra2_android_ptit.database.entity;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
@Entity(tableName = "tickets", foreignKeys = {
    @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE),
    @ForeignKey(entity = Showtime.class, parentColumns = "id", childColumns = "showtimeId", onDelete = ForeignKey.CASCADE)
})
public class Ticket {
    @PrimaryKey(autoGenerate = true) public int id;
    public int userId;
    public int showtimeId;
    public int seat;

    public double price;
}
