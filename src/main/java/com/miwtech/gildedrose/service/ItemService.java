package com.miwtech.gildedrose.service;

import com.miwtech.gildedrose.entity.ItemEntity;
import com.miwtech.gildedrose.entity.ItemInventoryEntity;
import com.miwtech.gildedrose.exception.InvalidItemException;
import com.miwtech.gildedrose.exception.InvalidItemPageException;
import com.miwtech.gildedrose.exception.ItemNotAvailableException;
import com.miwtech.gildedrose.repository.ItemInventoryRepository;
import com.miwtech.gildedrose.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The ItemService facilitates the retrieval and purchase of items
 */
@Service
@Transactional
public class ItemService {
    @Value("${items.per.page}")
    private int itemsPerPage;

    public final ItemRepository itemRepository;
    public final ItemInventoryRepository itemInventoryRepository;
    public final ViewLogService viewLogService;
    public final SurgePricingService surgePricingService;

    public ItemService(ItemRepository itemRepository, ItemInventoryRepository itemInventoryRepository, ViewLogService viewLogService, SurgePricingService surgePricingService) {
        this.itemRepository = itemRepository;
        this.itemInventoryRepository = itemInventoryRepository;
        this.viewLogService = viewLogService;
        this.surgePricingService = surgePricingService;
    }

    /**
     * Retrieve a page of items
     * @param page
     * @return
     * @throws InvalidItemPageException
     */
    public Page<ItemEntity> getItemInventory(int page) throws InvalidItemPageException {
        Page<ItemEntity> itemEntityPage = itemRepository.getInStockItemsPaginated(PageRequest.of(page-1, itemsPerPage));

        if (page > itemEntityPage.getTotalPages())
            throw new InvalidItemPageException("Invalid page selection. There are no items for this page.");
        List<ItemEntity> viewedItemEntities = itemEntityPage.getContent();

        viewLogService.addViewLogs(viewedItemEntities);
        surgePricingService.adjustPricing(itemEntityPage);

        return itemEntityPage;
    }
    public void featureNoLongerNeeded() {
        //TODO: Remove this!
    }
    /**
     * Purchase an item
     * @param itemId
     * @throws InvalidItemException
     * @throws ItemNotAvailableException
     */
    public void purchaseItem(Long itemId) throws InvalidItemException, ItemNotAvailableException {
        Optional<ItemInventoryEntity> optionalItemInventoryEntity = itemInventoryRepository.getItemInventoryByItemId(itemId);
        if (!optionalItemInventoryEntity.isPresent())
            throw new InvalidItemException("There are no items found matching the passed ID.");
        ItemInventoryEntity itemInventoryEntity = optionalItemInventoryEntity.get();
        int totalAvailable = itemInventoryEntity.getTotalAvailable();
        if (totalAvailable == 0)
            throw new ItemNotAvailableException("This item is no longer in stock.");

        itemInventoryEntity.setTotalAvailable(--totalAvailable);

        itemInventoryRepository.save(itemInventoryEntity);
    }
}
