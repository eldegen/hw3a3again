package com.example.hw3a3again.data.repos;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.hw3a3again.common.Resource;
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

    public MutableLiveData<Resource<MainResponse>> getWeather(String city, String apikey) {
        MutableLiveData<Resource<MainResponse>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading());
        api.getWeather(city, apikey).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.setValue(Resource.success(response.body()));
                } else {
                    liveData.setValue(Resource.error(response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                liveData.setValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });

        return liveData;
    }
}
