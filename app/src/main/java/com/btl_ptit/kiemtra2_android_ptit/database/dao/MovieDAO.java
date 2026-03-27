package com.btl_ptit.kiemtra2_android_ptit.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.btl_ptit.kiemtra2_android_ptit.database.entity.Movie;

import java.util.List;
@Dao
public interface MovieDAO {
    @Insert
    void insertMovie(Movie movie);
    @Query("SELECT * FROM movies")
    List<Movie> getAllMovies();

    @Query("Select * from movies where id = :id")
    Movie getMovieById(int id);

}
