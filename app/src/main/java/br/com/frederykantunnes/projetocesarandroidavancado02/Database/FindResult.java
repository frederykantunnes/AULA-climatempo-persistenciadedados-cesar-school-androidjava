package br.com.frederykantunnes.projetocesarandroidavancado02.Database;

import java.util.List;

import br.com.frederykantunnes.projetocesarandroidavancado02.Features.City.CityVO;

public class FindResult {
    public final List<CityVO> list;
    public FindResult (List<CityVO> list) {
        this.list = list;
    }
}
