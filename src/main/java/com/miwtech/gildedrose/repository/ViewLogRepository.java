package com.miwtech.gildedrose.repository;

import com.miwtech.gildedrose.entity.ViewLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ViewLogRepository extends JpaRepository<ViewLogEntity, Long> {

    @Query("select count(vl) from ViewLogEntity vl where vl.itemEntity.id = :itemId and vl.viewTime >= :dateTime")
    int getHourlyCount(Long itemId, LocalDateTime dateTime);
}

