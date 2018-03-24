
package com.example.pavel.loadmore.model.LaunchModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Payload implements Parcelable {

    @SerializedName("payload_id")
    @Expose
    private String payloadId;
    @SerializedName("reused")
    @Expose
    private Boolean reused;
    @SerializedName("customers")
    @Expose
    private List<String> customers = null;
    @SerializedName("payload_type")
    @Expose
    private String payloadType;
    @SerializedName("payload_mass_kg")
    @Expose
    private Object payloadMassKg;
    @SerializedName("payload_mass_lbs")
    @Expose
    private Object payloadMassLbs;
    @SerializedName("orbit")
    @Expose
    private String orbit;

    public String getPayloadId() {
        return payloadId;
    }

    public void setPayloadId(String payloadId) {
        this.payloadId = payloadId;
    }

    public Boolean getReused() {
        return reused;
    }

    public void setReused(Boolean reused) {
        this.reused = reused;
    }

    public List<String> getCustomers() {
        return customers;
    }

    public void setCustomers(List<String> customers) {
        this.customers = customers;
    }

    public String getPayloadType() {
        return payloadType;
    }

    public void setPayloadType(String payloadType) {
        this.payloadType = payloadType;
    }

    public Object getPayloadMassKg() {
        return payloadMassKg;
    }

    public void setPayloadMassKg(Object payloadMassKg) {
        this.payloadMassKg = payloadMassKg;
    }

    public Object getPayloadMassLbs() {
        return payloadMassLbs;
    }

    public void setPayloadMassLbs(Object payloadMassLbs) {
        this.payloadMassLbs = payloadMassLbs;
    }

    public String getOrbit() {
        return orbit;
    }

    public void setOrbit(String orbit) {
        this.orbit = orbit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.payloadId);
        dest.writeValue(this.reused);
        dest.writeStringList(this.customers);
        dest.writeString(this.payloadType);
//        dest.writeParcelable(this.payloadMassKg, flags);
//        dest.writeParcelable(this.payloadMassLbs, flags);
        dest.writeString(this.orbit);
    }

    public Payload() {
    }

    protected Payload(Parcel in) {
        this.payloadId = in.readString();
        this.reused = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.customers = in.createStringArrayList();
        this.payloadType = in.readString();
//        this.payloadMassKg = in.readParcelable(Object.class.getClassLoader());
//        this.payloadMassLbs = in.readParcelable(Object.class.getClassLoader());
        this.orbit = in.readString();
    }

    public static final Creator<Payload> CREATOR = new Creator<Payload>() {
        @Override
        public Payload createFromParcel(Parcel source) {
            return new Payload(source);
        }

        @Override
        public Payload[] newArray(int size) {
            return new Payload[size];
        }
    };
}
