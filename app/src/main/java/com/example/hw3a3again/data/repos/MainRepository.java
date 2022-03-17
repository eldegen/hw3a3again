package com.example.hw3a3again.data.repos;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.hw3a3again.data.models.MainResponse;
import com.example.hw3a3again.data.remote.WeatherApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    private WeatherApi api;

    public MainRepository(WeatherApi api) {
        this.api = api;
    }

    public MutableLiveData<MainResponse> getWeather(String city, String apikey) {
        MutableLiveData<MainResponse> liveData = new MutableLiveData<>();
        api.getWeather(city, apikey).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("f_global", "success");
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {

            }
        });

        return liveData;
    }
}
