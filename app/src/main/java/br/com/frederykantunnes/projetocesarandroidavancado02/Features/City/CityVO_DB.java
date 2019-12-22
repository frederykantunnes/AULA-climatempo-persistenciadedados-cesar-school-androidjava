package br.com.frederykantunnes.projetocesarandroidavancado02.Features.City;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CityVO_DB {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "name")
    public String name;
}
