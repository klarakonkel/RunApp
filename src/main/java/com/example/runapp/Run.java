package com.example.runapp;

import java.time.LocalDate;

public class Run {
    double distance;
    double time;
    LocalDate date;



    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Run(double distance, double time, LocalDate date) {
        this.distance = distance;
        this.time = time;
        this.date = date;
    }
    @Override
    public String toString() {
        return "Run{" +
                "distance=" + distance +
                ", time=" + time +
                ", date=" + date +
                '}';
    }
}
