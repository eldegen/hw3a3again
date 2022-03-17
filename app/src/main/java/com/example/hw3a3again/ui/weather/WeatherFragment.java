package com.example.hw3a3again.ui.weather;

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

public class WeatherFragment extends Fragment {

    private FragmentWeatherBinding binding;
    private WeatherViewModel viewModel;

    private String city = "Bishkek";
    private String apikey = "7b6537fda7b672d63330155ecf0bdaf8";

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

        viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        viewModel.getWeather(city, apikey);
        viewModel.weatherLiveData.observe(getViewLifecycleOwner(), new Observer<MainResponse>() {
            @Override
            public void onChanged(MainResponse mainResponse) {
                Log.e("f_global", "WeatherFragment: Success!");
            }
        });
        
    }
}