package com.example.hw3a3again.ui.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.hw3a3again.common.Resource;
import com.example.hw3a3again.data.models.forecast.ForecastMainResponse;
import com.example.hw3a3again.data.models.weather.MainResponse;
import com.example.hw3a3again.data.repos.MainRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class WeatherViewModel extends ViewModel {

    private MainRepository repository;

    @Inject
    public WeatherViewModel(MainRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<MainResponse>> weatherLiveData;
    public LiveData<Resource<ForecastMainResponse>> forecastLiveData;

    public void getWeather(String city, String apikey) {
        weatherLiveData = repository.getWeather(city, apikey);
    }

    public void getForecast(double lat, double lon, String apikey) {
        forecastLiveData = repository.getForecast(lat, lon, apikey);
    }
}
