package com.SEACORP.EastSea.Models.EastSeaRecord;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder

public class EastSeaRecord {
    @Id
    private Long id;

    private String year;
    private String month;
    private String day;
    private String lat;
    private String lon;
    private String vgs;
    private String ugs;
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
}

