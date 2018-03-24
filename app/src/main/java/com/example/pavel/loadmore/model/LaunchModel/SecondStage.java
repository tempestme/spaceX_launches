
package com.example.pavel.loadmore.model.LaunchModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SecondStage {

    @SerializedName("payloads")
    @Expose
    private List<Payload> payloads = null;

    public List<Payload> getPayloads() {
        return payloads;
    }

    public void setPayloads(List<Payload> payloads) {
        this.payloads = payloads;
    }

}
