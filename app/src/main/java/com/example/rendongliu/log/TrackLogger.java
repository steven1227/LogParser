package com.example.rendongliu.log;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rendong.liu on 30/06/15.
 */
public class TrackLogger {
    private List<TrackInfo> trackInfoList;

    public TrackLogger(){
        this.trackInfoList =new ArrayList<>();
    }

    public List<TrackInfo> getTrackInfoList() {
        return trackInfoList;
    }

    public boolean add(TrackInfo object) {
        return trackInfoList.add(object);
    }

    public String parsetToJson(){
        if(trackInfoList.isEmpty())
        {
            return null;
        }else {
            Gson gson = new Gson();
            return gson.toJson(this.trackInfoList);
        }
    }

    public void clean(){
        this.trackInfoList.clear();
    }


    public TrackLogger getEventLogger() {
        TrackLogger newLogger = new TrackLogger();

        for (TrackInfo info : trackInfoList) {
            if (info instanceof TrackEvent) {
                newLogger.add(info);
            }
        }
        return newLogger;
    }

    public TrackLogger getPageLogger() {
        TrackLogger newLogger = new TrackLogger();

        for (TrackInfo info : trackInfoList) {
            if (info instanceof TrackPage) {
                newLogger.add(info);
            }
        }
        return newLogger;
    }
}
