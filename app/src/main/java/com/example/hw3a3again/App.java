package com.example.hw3a3again;

import android.app.Application;

import com.example.hw3a3again.data.remote.RetrofitClient;
import com.example.hw3a3again.data.repos.MainRepository;

public class App extends Application {

    private RetrofitClient retrofitClient;
    public static MainRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofitClient = new RetrofitClient();
        repository = new MainRepository(retrofitClient.api);
    }
}
