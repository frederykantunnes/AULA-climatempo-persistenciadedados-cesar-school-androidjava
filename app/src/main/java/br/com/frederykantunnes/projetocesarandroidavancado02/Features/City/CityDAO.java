package br.com.frederykantunnes.projetocesarandroidavancado02.Features.City;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CityDAO {
    @Query("SELECT * FROM CityVO_DB")
    List<CityVO_DB> getAll();

    @Insert
    void insert(CityVO_DB city);

    @Delete
    void delete(CityVO_DB city);
}