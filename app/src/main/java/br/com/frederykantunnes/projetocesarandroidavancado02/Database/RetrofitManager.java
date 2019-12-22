package br.com.frederykantunnes.projetocesarandroidavancado02.Database;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RetrofitManager {
    private static final String API_URL =
            "http://api.openweathermap.org/data/2.5/";
    public static final String API_KEY =
            "f0e2cd2bd36f4a90bca0e2d58f2d94f1";
    public interface WeatherService {
        @GET("find")
        Call<FindResult> find(
                @Query("q") String cityName,
                @Query("lang") String lang,
                @Query("units") String unit,
                @Query("appid") String apiKey);


        @GET("group")
        Call<FindResult> findFavorites(
                @Query("id") String cityName,
                @Query("lang") String lang,
                @Query("units") String unit,
                @Query("appid") String apiKey);
    }
    public static WeatherService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(WeatherService.class);
    }
}