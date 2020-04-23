package com.miwtech.gildedrose.service;

import com.miwtech.gildedrose.entity.ItemEntity;
import com.miwtech.gildedrose.entity.ViewLogEntity;
import com.miwtech.gildedrose.repository.ViewLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * The ViewLogService records the view of items. The hourly count is returned by the surge pricing process.
 */
@Service
public class ViewLogService {
    private final ViewLogRepository viewLogRepository;

    public ViewLogService(ViewLogRepository viewLogRepository) {
        this.viewLogRepository = viewLogRepository;
    }

    public void addViewLogs(List<ItemEntity> itemEntities) {
        List<ViewLogEntity> viewLogEntities = new ArrayList<>();

        for(ItemEntity itemEntity : itemEntities) {
            ViewLogEntity viewLogEntity = new ViewLogEntity();
            viewLogEntity.setItemEntity(itemEntity);
            viewLogEntity.setViewTime(LocalDateTime.now());
            viewLogEntities.add(viewLogEntity);
        }
        viewLogRepository.saveAll(viewLogEntities);
    }
    public int getHourlyViewCount(ItemEntity itemEntity) {
        LocalDateTime oneHourAgo = LocalDateTime.now().minus(1, ChronoUnit.HOURS);
        return viewLogRepository.getHourlyCount(itemEntity.getId(),oneHourAgo);
    }
}
