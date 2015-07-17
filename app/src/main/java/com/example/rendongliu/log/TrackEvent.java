package com.example.rendongliu.log;

/**
 * Created by rendong.liu on 30/06/15.
 */
public class TrackEvent extends TrackInfo{

    private String category;
    private String action;
    private String label;
    private long value;


    public TrackEvent () {
    }

    public TrackEvent (String category,String action, String label, long value,String time){
        this.category=category;
        this.action=action;
        this.label=label;
        this.value=value;
        setTime(time);
    }

    @Override
    public void parseJson() {

    }

    @Override
    public String gettitle() {
        return this.action;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
