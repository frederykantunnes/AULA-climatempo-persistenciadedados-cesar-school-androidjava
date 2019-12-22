package br.com.frederykantunnes.projetocesarandroidavancado02.Features.Objects;

public class Main {
    public Double temp, temp_max, temp_min, pressure;
    public int humidity;


    public Main(Double temp, Double temp_max, Double temp_min, int humidity, Double pressure) {
        this.temp = temp;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
        this.humidity = humidity;
        this.pressure = pressure;
    }
}
