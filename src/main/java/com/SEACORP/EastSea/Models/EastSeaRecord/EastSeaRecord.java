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
    private double ugs;
    private double adts;
}

