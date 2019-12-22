package br.com.frederykantunnes.projetocesarandroidavancado02.Features.City;

import br.com.frederykantunnes.projetocesarandroidavancado02.Database.AppDatabase;

public class CityDAO_DB {

    public static void inserir(AppDatabase appDatabase, CityVO cityVO_db){
        CityVO_DB cityDB = new CityVO_DB();
        cityDB.id = cityVO_db.id;
        cityDB.name = cityVO_db.name;
        appDatabase.cityDao().insert(cityDB);
    }

    public static void remover(AppDatabase appDatabase, CityVO cityVO_db){
        CityVO_DB cityDB = new CityVO_DB();
        cityDB.id = cityVO_db.id;
        cityDB.name = cityVO_db.name;
        appDatabase.cityDao().delete(cityDB);
    }
}
