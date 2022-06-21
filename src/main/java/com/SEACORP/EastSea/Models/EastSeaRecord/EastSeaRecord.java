package com.SEACORP.EastSea.Models.EastSeaRecord;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity(name = "eastsea_record")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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
}

