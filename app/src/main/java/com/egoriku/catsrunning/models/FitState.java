package com.egoriku.catsrunning.models;

import com.egoriku.catsrunning.data.commons.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FitState {

    private static FitState fitState;

    private long sinceTime;
    private int nowDistance;
    private long startTime;
    private boolean isFitRun;
    private List<LatLng> latLngs = new ArrayList<>();
    private long timeBetweenLocations;
    private long weight;
    private double calories;
    private int typeFit;

    public static FitState getInstance() {
        if (fitState == null) {
            fitState = new FitState();
        }

        return fitState;
    }

    public int getTypeFit() {
        return typeFit;
    }

    public void setTypeFit(int typeFit) {
        this.typeFit = typeFit;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public long getTimeBetweenLocations() {
        return timeBetweenLocations;
    }

    public void setTimeBetweenLocations(long timeBetweenLocations) {
        this.timeBetweenLocations = timeBetweenLocations;
    }

    public boolean isFitRun() {
        return isFitRun;
    }

    public void setFitRun(boolean fitRun) {
        isFitRun = fitRun;
    }

    public List<LatLng> getLatLngs() {
        return latLngs;
    }

    public void addPoint(LatLng latLng) {
        latLngs.add(latLng);
    }

    public long getSinceTime() {
        return sinceTime;
    }

    public void setSinceTime(long sinceTime) {
        this.sinceTime = sinceTime;
    }

    public void setNowDistance(int nowDistance) {
        this.nowDistance = nowDistance;
    }

    public float getNowDistance() {
        return nowDistance;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void clearFitData() {
        sinceTime = 0;
        nowDistance = 0;
        startTime = 0;
        latLngs = new ArrayList<>();
        timeBetweenLocations = 0;
        weight = 0;
        calories = 0;
        typeFit = 1;
    }
}
