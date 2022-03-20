package com.example.hw3a3again.data.remote;

import com.example.hw3a3again.data.models.forecast.ForecastMainResponse;
import com.example.hw3a3again.data.models.weather.MainResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("weather")
    Call<MainResponse> getWeather(
            @Query("q") String city,
            @Query("appid") String apikey
    );

    @GET("forecast")
    Call<ForecastMainResponse> getForecast(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String apikey
    );

}
