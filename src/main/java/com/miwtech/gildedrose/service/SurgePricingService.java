package com.miwtech.gildedrose.service;

import com.miwtech.gildedrose.entity.ItemEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The SurgePricingService adjusts the price of items if they are viewed more frequently than X times per hours.
 */
@Service
public class SurgePricingService {
    @Value("${surge.pricing.threshold}")
    private int surgePricingThreshold;
    @Value("${surge.price.factor}")
    private BigDecimal surgePriceFactor;

    public final ViewLogService viewLogService;

    public SurgePricingService(ViewLogService viewLogService) {
        this.viewLogService = viewLogService;
    }

    /**
     * For each page of items, check to see if they have been viewed more than
     * 10 times in the last hour. If so, increase the price by 10%.
     * @param itemEntityPage
     */
    public void adjustPricing(Page<ItemEntity> itemEntityPage) {
        for(ItemEntity itemEntity : itemEntityPage.getContent()) {
            int hourlyCount = viewLogService.getHourlyViewCount(itemEntity);
            if (hourlyCount>surgePricingThreshold) {
                BigDecimal currentPrice = itemEntity.getPrice();
                BigDecimal surgePrice = currentPrice.multiply(surgePriceFactor).setScale(2, RoundingMode.HALF_UP);
                itemEntity.setPrice(surgePrice);
            }
        }
    }
}
