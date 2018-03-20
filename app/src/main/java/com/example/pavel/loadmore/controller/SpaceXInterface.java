package com.example.pavel.loadmore.controller;

import com.example.pavel.loadmore.model.Launch;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pavel on 20.02.18.
 */

public interface SpaceXInterface {
    String Base_url = "https://api.spacexdata.com/";

    @GET("/v2/launches")
    Call<ArrayList<Launch>> getLatestLaunch(@Query("launch_year")String year);

    @GET("/v2/launches/upcoming")
    Call<ArrayList<Launch>> getUpcomingLaunch();
}
