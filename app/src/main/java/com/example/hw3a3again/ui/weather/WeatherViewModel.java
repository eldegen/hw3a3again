package com.example.hw3a3again.ui.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.hw3a3again.App;
import com.example.hw3a3again.data.models.MainResponse;

public class WeatherViewModel extends ViewModel {

    public LiveData<MainResponse> weatherLiveData;

    public void getWeather(String city, String apikey) {
        weatherLiveData = App.repository.getWeather(city, apikey);
    }
}
