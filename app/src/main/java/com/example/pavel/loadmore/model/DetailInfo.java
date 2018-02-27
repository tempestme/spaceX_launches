package com.example.pavel.loadmore.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pavel on 24.02.18.
 */

public class DetailInfo implements Parcelable {
    String rocketName;
    String site;
    String date;
    String details;
    boolean core;
    boolean sidecore1;
    boolean sidecore2;
    ArrayList<Payload> payloads;
    String articleLink;
    String pressKit;
    String reddit;
    String reddit2;

    public DetailInfo(String rocketName, String site, String date, String details, boolean core, boolean sidecore1, boolean sidecore2, ArrayList<Payload> payloads, String articleLink, String pressKit, String reddit, String reddit2) {
        this.rocketName = rocketName;
        this.site = site;
        this.date = date;
        this.details = details;
        this.core = core;
        this.sidecore1 = sidecore1;
        this.sidecore2 = sidecore2;
        this.payloads = payloads;
        this.articleLink = articleLink;
        this.pressKit = pressKit;
        this.reddit = reddit;
        this.reddit2 = reddit2;
    }

    public String getRocketName() {
        return rocketName;
    }

    public String getSite() {
        return site;
    }

    public String getDate() {
        return date;
    }

    public String getDetails() {
        return details;
    }

    public boolean isCore() {
        return core;
    }

    public boolean isSidecore1() {
        return sidecore1;
    }

    public boolean isSidecore2() {
        return sidecore2;
    }

    public List<Payload> getPayloads() {
        return payloads;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public String getPressKit() {
        return pressKit;
    }

    public String getReddit() {
        return reddit;
    }

    public String getReddit2() {
        return reddit2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.rocketName);
        dest.writeString(this.site);
        dest.writeString(this.date);
        dest.writeString(this.details);
        dest.writeByte(this.core ? (byte) 1 : (byte) 0);
        dest.writeByte(this.sidecore1 ? (byte) 1 : (byte) 0);
        dest.writeByte(this.sidecore2 ? (byte) 1 : (byte) 0);
        dest.writeList(this.payloads);
        dest.writeString(this.articleLink);
        dest.writeString(this.pressKit);
        dest.writeString(this.reddit);
        dest.writeString(this.reddit2);
    }

    public DetailInfo() {
    }

    protected DetailInfo(Parcel in) {
        this.rocketName = in.readString();
        this.site = in.readString();
        this.date = in.readString();
        this.details = in.readString();
        this.core = in.readByte() != 0;
        this.sidecore1 = in.readByte() != 0;
        this.sidecore2 = in.readByte() != 0;
        this.payloads = new ArrayList<Payload>();
        in.readList(this.payloads, Payload.class.getClassLoader());
        this.articleLink = in.readString();
        this.pressKit = in.readString();
        this.reddit = in.readString();
        this.reddit2 = in.readString();
    }

    public static final Creator<DetailInfo> CREATOR = new Creator<DetailInfo>() {
        @Override
        public DetailInfo createFromParcel(Parcel source) {
            return new DetailInfo(source);
        }

        @Override
        public DetailInfo[] newArray(int size) {
            return new DetailInfo[size];
        }
    };
}
