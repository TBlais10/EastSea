package com.SEACORP.EastSea.Config;

import com.SEACORP.EastSea.Models.EastSeaRecord.EastSeaRecord;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.net.BindException;

public class EastSeaSetMapper implements FieldSetMapper<EastSeaRecord> {
    @Override
        public EastSeaRecord mapFieldSet(FieldSet fieldSet) {
        return new EastSeaRecord(
                fieldSet.readDouble("year"),
                fieldSet.readDouble("month"),
                fieldSet.readDouble("day"),
                fieldSet.readDouble("lat"),
                fieldSet.readDouble("lon"),
                fieldSet.readDouble("vgs"),
                fieldSet.readDouble("adts")
        );
    }
}
