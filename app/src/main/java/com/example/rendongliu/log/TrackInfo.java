package com.example.rendongliu.log;

import android.os.Parcelable;

/**
 * Created by rendong.liu on 30/06/15.
 */
public abstract class TrackInfo {

    private String time;

    public String getTime() {
        return time;
    }

    protected abstract void parseJson();

    public void setTime(String time) {
        this.time = time;
    }

    public abstract String gettitle();
}
