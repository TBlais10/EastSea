package com.SEACORP.EastSea.Models.EastSea;

public class EastSea {
    private double year;
    private double month;
    private double day;
    private double lat;
    private double lon;
    private double vgs;
    private double adts;

    public EastSea() {
    }

    public EastSea(double year, double month, double day, double lat, double lon, double vgs, double adts) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.lat = lat;
        this.lon = lon;
        this.vgs = vgs;
        this.adts = adts;
    }

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
