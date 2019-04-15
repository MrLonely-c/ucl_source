package com.example.helloworld.UCLclasses;

public class ProductionState {
    private String RecordID;
    private String MonitorId;
    private String State;
    private String HealthState;
    private String GPSLocation;
    private String ActiveDis;
    private String Weight;
    private String BodyTemperature;
    private String UCLLink;
    private String MonitorRecordTime;
    private String Flag;

    public String getRecordID() {
        return RecordID;
    }

    public void setRecordID(String recordID) {
        RecordID = recordID;
    }

    public String getMonitorId() {
        return MonitorId;
    }

    public void setMonitorId(String monitorId) {
        MonitorId = monitorId;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getHealthState() {
        return HealthState;
    }

    public void setHealthState(String healthState) {
        HealthState = healthState;
    }

    public String getGPSLocation() {
        return GPSLocation;
    }

    public void setGPSLocation(String GPSLocation) {
        this.GPSLocation = GPSLocation;
    }

    public String getActiveDis() {
        return ActiveDis;
    }

    public void setActiveDis(String activeDis) {
        ActiveDis = activeDis;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getBodyTemperature() {
        return BodyTemperature;
    }

    public void setBodyTemperature(String bodyTemperature) {
        BodyTemperature = bodyTemperature;
    }

    public String getUCLLink() {
        return UCLLink;
    }

    public void setUCLLink(String UCLLink) {
        this.UCLLink = UCLLink;
    }

    public String getMonitorRecordTime() {
        return MonitorRecordTime;
    }

    public void setMonitorRecordTime(String monitorRecordTime) {
        MonitorRecordTime = monitorRecordTime;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public ProductionState(String recordID, String monitorId, String state, String healthState, String GPSLocation, String activeDis, String weight, String bodyTemperature, String UCLLink, String monitorRecordTime, String flag) {
        RecordID = recordID;
        MonitorId = monitorId;
        State = state;
        HealthState = healthState;
        this.GPSLocation = GPSLocation;
        ActiveDis = activeDis;
        Weight = weight;
        BodyTemperature = bodyTemperature;
        this.UCLLink = UCLLink;
        MonitorRecordTime = monitorRecordTime;
        Flag = flag;
    }
}
