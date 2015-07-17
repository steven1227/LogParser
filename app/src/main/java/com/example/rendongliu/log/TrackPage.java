package com.example.rendongliu.log;

/**
 * Created by rendong.liu on 30/06/15.
 */
public class TrackPage extends TrackInfo {
    private String pagename;

    public TrackPage (){

    }
    public String getPagename() {
        return pagename;
    }

    public void setPagename(String pagename) {
        this.pagename = pagename;
    }

    public TrackPage(String pagename,String time){
        this.pagename=pagename;
        setTime(time);
    }

    @Override
    public void parseJson() {

    }

    @Override
    public String gettitle() {
        return this.pagename;
    }
}
