package com.SEACORP.EastSea.Processor;

import com.SEACORP.EastSea.Models.EastSeaRecord.EastSeaRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class EastSeaProcessor implements ItemProcessor<EastSeaRecord, EastSeaRecord> {

    private static final Logger logger = LoggerFactory.getLogger(EastSeaProcessor.class);

    @Override
    public EastSeaRecord process(EastSeaRecord item) throws Exception{
        final String year = item.getYear();
        final String month = item.getMonth();
        final String day = item.getDay();
        final String lat = item.getLat();
        final String lon = item.getLon();
        final String vgs = item.getVgs();
        final String ugs = item.getUgs();
        final String adts = item.getAdts();

        final EastSeaRecord transformedData = new EastSeaRecord(year, month, day, lat, lon, vgs, ugs, adts);
        logger.info("Converting (" + item + ") into (" + transformedData + " )" );

        return transformedData;
    }

}
