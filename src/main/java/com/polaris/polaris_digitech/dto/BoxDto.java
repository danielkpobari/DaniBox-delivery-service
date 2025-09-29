package com.polaris.polaris_digitech.dto;

import com.polaris.polaris_digitech.model.BoxState;

public class BoxDto {
    private String txref;
    private Integer weightLimit;
    private Integer batteryCapacity;
    private BoxState state;

    public BoxDto() {}

    public BoxDto(String txref, Integer weightLimit, Integer batteryCapacity, BoxState state) {
        this.txref = txref;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
    }

    public String getTxref() { return txref; }
    public void setTxref(String txref) { this.txref = txref; }

    public Integer getWeightLimit() { return weightLimit; }
    public void setWeightLimit(Integer weightLimit) { this.weightLimit = weightLimit; }

    public Integer getBatteryCapacity() { return batteryCapacity; }
    public void setBatteryCapacity(Integer batteryCapacity) { this.batteryCapacity = batteryCapacity; }

    public BoxState getState() { return state; }
    public void setState(BoxState state) { this.state = state; }
}