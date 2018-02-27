
package com.example.pavel.loadmore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Telemetry {

    @SerializedName("flight_club")
    @Expose
    private String flightClub;

    public String getFlightClub() {
        return flightClub;
    }

    public void setFlightClub(String flightClub) {
        this.flightClub = flightClub;
    }

}
