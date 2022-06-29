package com.SEACORP.EastSea.Models.EastSeaRecord;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EastSeaRecord {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private String year;

    @Column(name = "month")
    private String month;

    @Column(name = "day")
    private String day;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lon")
    private String lon;

    @Column(name = "vgs")
    private String vgs;

    @Column(name = "ugs")
    private String ugs;

    @Column(name = "adts")
    private String adts;

    public EastSeaRecord(String year, String month, String day, String lat, String lon, String vgs, String ugs, String adts) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.lat = lat;
        this.lon = lon;
        this.vgs = vgs;
        this.ugs = ugs;
        this.adts = adts;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getVgs() {
        return vgs;
    }

    public void setVgs(String vgs) {
        this.vgs = vgs;
    }

    public String getUgs() {
        return ugs;
    }

    public void setUgs(String ugs) {
        this.ugs = ugs;
    }

    public String getAdts() {
        return adts;
    }

    public void setAdts(String adts) {
        this.adts = adts;
    }
}

