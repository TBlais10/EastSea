package com.SEACORP.EastSea.Repositories;

import com.SEACORP.EastSea.Models.EastSeaRecord.EastSeaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EastSeaRecordRepository extends JpaRepository<EastSeaRecord, Long> {
}
