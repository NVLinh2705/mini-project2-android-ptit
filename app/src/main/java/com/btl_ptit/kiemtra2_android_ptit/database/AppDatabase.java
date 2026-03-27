package com.btl_ptit.kiemtra2_android_ptit.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.btl_ptit.kiemtra2_android_ptit.database.dao.MovieDAO;
import com.btl_ptit.kiemtra2_android_ptit.database.dao.ShowtimeDAO;
import com.btl_ptit.kiemtra2_android_ptit.database.dao.TheaterDAO;
import com.btl_ptit.kiemtra2_android_ptit.database.dao.TicketDAO;
import com.btl_ptit.kiemtra2_android_ptit.database.dao.UserDAO;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Movie;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Showtime;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Theater;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.Ticket;
import com.btl_ptit.kiemtra2_android_ptit.database.entity.User;
import com.btl_ptit.kiemtra2_android_ptit.utils.Constants;
import com.btl_ptit.kiemtra2_android_ptit.utils.Converters;

import androidx.annotation.NonNull;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.Executors;
import java.time.LocalDateTime;

@Database(entities = {User.class, Movie.class, Theater.class, Showtime.class, Ticket.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract MovieDAO movieDAO();
    public abstract TheaterDAO theaterDAO();
    public abstract ShowtimeDAO showtimeDAO();
    public abstract TicketDAO ticketDAO();
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            Constants.databaseName
                    )
                    .fallbackToDestructiveMigration()
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadExecutor().execute(() -> {
                                AppDatabase database = AppDatabase.getInstance(context);
                                
                                User user = new User("admin", "admin");
                                database.userDAO().insertUser(user);

                                Movie m1 = new Movie();
                                m1.title = "Dune: Part Two";
                                m1.description = "Paul Atreides unites with Chani and the Fremen...";
                                m1.imageUrl = "https://m.media-amazon.com/images/I/71eHZFw+GlL._AC_SY741_.jpg";
                                database.movieDAO().insertMovie(m1);

                                Movie m2 = new Movie();
                                m2.title = "Vintage Movie";
                                m2.description = "A classic vintage film.";
                                m2.imageUrl = "https://www.vintagemovieposters.co.uk/wp-content/uploads/2020/11/IMG_5878-scaled.jpeg";
                                database.movieDAO().insertMovie(m2);

                                Movie m3 = new Movie();
                                m3.title = "Interstellar";
                                m3.description = "A team of explorers travel through a wormhole in space...";
                                m3.imageUrl = "https://m.media-amazon.com/images/I/81vRg6RVaFL._AC_SY879_.jpg";
                                database.movieDAO().insertMovie(m3);

                                Theater t1 = new Theater();
                                t1.name = "CGV Vincom";
                                t1.address = "Vincom Center";
                                database.theaterDAO().insertTheater(t1);

                                Theater t2 = new Theater();
                                t2.name = "Lotte Cinema";
                                t2.address = "Lotte Mall";
                                database.theaterDAO().insertTheater(t2);

                                Theater t3 = new Theater();
                                t3.name = "BHD Star";
                                t3.address = "Discovery Complex";
                                database.theaterDAO().insertTheater(t3);

                                for(int i=1; i<=3; i++) {
                                    for(int j=1; j<=3; j++) {
                                        Showtime s = new Showtime();
                                        s.theaterId = i;
                                        s.movieId = j;
                                        s.startTime = LocalDateTime.now().plusDays(i).plusHours(j);
                                        s.endTime = s.startTime.plusHours(2);
                                        s.seatNum = 50;
                                        s.price = 100.00;
                                        database.showtimeDAO().insertShowtime(s);
                                    }
                                }
                            });
                        }
                    })
                    .build();
        }
        return instance;
    }
}
