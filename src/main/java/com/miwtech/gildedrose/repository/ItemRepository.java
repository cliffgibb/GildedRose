package com.miwtech.gildedrose.repository;

import com.miwtech.gildedrose.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<ItemEntity, Long> {
    @Query("select new ItemEntity(i.id, i.name, i.description, i.price) from ItemEntity i join ItemInventoryEntity ii on i.id = ii.itemEntity.id where ii.totalAvailable > 0")
    Page<ItemEntity> getInStockItemsPaginated(PageRequest page);
}