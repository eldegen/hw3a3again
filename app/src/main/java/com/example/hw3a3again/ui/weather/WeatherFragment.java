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
import android.widget.Toast;

import com.example.hw3a3again.App;
import com.example.hw3a3again.R;
import com.example.hw3a3again.common.Resource;
import com.example.hw3a3again.data.models.MainResponse;
import com.example.hw3a3again.databinding.FragmentWeatherBinding;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
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

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String timeText = timeFormat.format(currentDate);

        viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        viewModel.getWeather(city, apikey);
        viewModel.weatherLiveData.observe(getViewLifecycleOwner(), new Observer<Resource<MainResponse>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(Resource<MainResponse> mainResponse) {
                switch (mainResponse.status) {
                    case LOADING: {
                        Toast.makeText(getContext(), "Loading", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case SUCCESS: {
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        Log.e("f_global", "WeatherFragment: Success!");

                        binding.tvDateTime.setText(dateText + " | " + timeText);

                        double tempBuffer = mainResponse.data.getMain().getTemp() - 273.15;
                        double tempFeelsBuffer = mainResponse.data.getMain().getFeelsLike() - 273.15;

                        binding.tvWeatherType.setText(mainResponse.data.getWeather().get(0).getMain());
                        binding.tvTemp.setText(round(tempBuffer, 1) + "");
                        binding.tvFeels.setText(round(tempFeelsBuffer, 1) + "");
                        binding.tvHumidity.setText(mainResponse.data.getMain().getHumidity().toString() + "%");
                        binding.tvWind.setText(mainResponse.data.getWind().getSpeed().toString() + "m/s");

                        break;
                    }
                    case ERROR: {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
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