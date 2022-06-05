package com.SEACORP.EastSea.Models.EastSeaRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EastSeaRecord {
    private double year;
    private double month;
    private double day;
    private double lat;
    private double lon;
    private double vgs;
    private double adts;

    public double getYear() {
        return year;
    }

    public void setYear(double year) {
        this.year = year;
    }

    public double getMonth() {
        return month;
    }

    public void setMonth(double month) {
        this.month = month;
    }

    public double getDay() {
        return day;
    }

    public void setDay(double day) {
        this.day = day;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getVgs() {
        return vgs;
    }

    public void setVgs(double vgs) {
        this.vgs = vgs;
    }

    public double getAdts() {
        return adts;
    }

    public void setAdts(double adts) {
        this.adts = adts;
    }
}
