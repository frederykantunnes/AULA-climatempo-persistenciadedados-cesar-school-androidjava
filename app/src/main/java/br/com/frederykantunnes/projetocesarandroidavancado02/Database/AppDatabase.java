package br.com.frederykantunnes.projetocesarandroidavancado02.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import br.com.frederykantunnes.projetocesarandroidavancado02.Features.City.CityDAO;
import br.com.frederykantunnes.projetocesarandroidavancado02.Features.City.CityVO_DB;

@Database(entities = {CityVO_DB.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CityDAO cityDao();
}