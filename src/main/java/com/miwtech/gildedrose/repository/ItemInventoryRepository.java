package com.miwtech.gildedrose.repository;

import com.miwtech.gildedrose.entity.ItemInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemInventoryRepository  extends JpaRepository<ItemInventoryEntity, Long> {

    @Query("from ItemInventoryEntity ii where ii.itemEntity.id = :itemId ")
    Optional<ItemInventoryEntity> getItemInventoryByItemId(Long itemId);
}
