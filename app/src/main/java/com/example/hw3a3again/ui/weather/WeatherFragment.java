package com.example.hw3a3again.ui.weather;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw3a3again.App;
import com.example.hw3a3again.R;
import com.example.hw3a3again.data.models.MainResponse;
import com.example.hw3a3again.databinding.FragmentWeatherBinding;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class WeatherFragment extends Fragment {

    private FragmentWeatherBinding binding;
    private WeatherViewModel viewModel;

    private String city = "Bishkek";
    private String apikey = "7b6537fda7b672d63330155ecf0bdaf8";

    double PI = 3.1415;

    public WeatherFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DecimalFormat df = new DecimalFormat("###.###");

        viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        viewModel.getWeather(city, apikey);
        viewModel.weatherLiveData.observe(getViewLifecycleOwner(), new Observer<MainResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(MainResponse mainResponse) {
                Log.e("f_global", "WeatherFragment: Success!");

                double tempBuffer = mainResponse.getMain().getTemp() - 273.15;
                double tempFeelsBuffer = mainResponse.getMain().getFeelsLike() - 273.15;

                binding.tvTemp.setText(round(tempBuffer, 1) + "°C");
                binding.tvFeelsTemp.setText("Feels like: " + round(tempFeelsBuffer, 1) + "°C");
                binding.tvCountry.setText("Country: " + mainResponse.getSys().getCountry());
                binding.tvHumidity.setText("Humidity: " + mainResponse.getMain().getHumidity().toString() + "%");
                binding.tvWindSpeed.setText("Wind speed: " + mainResponse.getWind().getSpeed().toString() + "m/s");
                binding.tvLongitude.setText("Longitude: " + mainResponse.getCoord().getLon().toString());
                binding.tvLatitude.setText("Latitude: " + mainResponse.getCoord().getLat().toString());
            }
        });
        
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}