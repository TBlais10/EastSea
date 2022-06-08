package com.SEACORP.EastSea.Config;

import com.SEACORP.EastSea.Models.EastSeaRecord.EastSeaRecord;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class EastSeaSetMapper implements FieldSetMapper<EastSeaRecord> {
    @Override
        public EastSeaRecord mapFieldSet(FieldSet fieldSet) throws BindException {
        return new EastSeaRecord(
                fieldSet.readString("year"),
                fieldSet.readString("month"),
                fieldSet.readString("day"),
                fieldSet.readString("lat"),
                fieldSet.readString("lon"),
                fieldSet.readString("vgs"),
                fieldSet.readString("ugs"),
                fieldSet.readString("adts")
        );
    }
}
