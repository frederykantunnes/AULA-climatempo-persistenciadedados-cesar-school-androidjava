package br.com.frederykantunnes.projetocesarandroidavancado02.Features.City;




import java.util.List;

import br.com.frederykantunnes.projetocesarandroidavancado02.Features.Objects.Clouds;
import br.com.frederykantunnes.projetocesarandroidavancado02.Features.Objects.Main;
import br.com.frederykantunnes.projetocesarandroidavancado02.Features.Objects.Sys;
import br.com.frederykantunnes.projetocesarandroidavancado02.Features.Objects.Weather;
import br.com.frederykantunnes.projetocesarandroidavancado02.Features.Objects.Wind;

public class CityVO {

    public Integer id;
    public String name;
    public List<Weather> weather;
    public Main main;
    public Clouds clouds;
    public Wind wind;
    public Sys sys;

    public CityVO(Integer id, String name, List<Weather> weather, Main main, Wind wind, Clouds clouds, Sys sys) {
        this.id = id;
        this.name = name;
        this.weather = weather;
        this.main = main;
        this.wind = wind;
        this.clouds = clouds;
        this.sys = sys;
    }
}
