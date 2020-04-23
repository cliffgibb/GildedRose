package com.miwtech.gildedrose;

import com.miwtech.gildedrose.entity.ItemEntity;
import com.miwtech.gildedrose.entity.ItemInventoryEntity;
import com.miwtech.gildedrose.entity.ViewLogEntity;
import com.miwtech.gildedrose.repository.ItemInventoryRepository;
import com.miwtech.gildedrose.repository.ItemRepository;
import com.miwtech.gildedrose.repository.ViewLogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class IntegrationTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ItemInventoryRepository itemInventoryRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ViewLogRepository viewLogRepository;

    @Test
    public void addItem_thenReturn() {
        ItemEntity itemEntity = new ItemEntity("test name", "test description", 55);
        ItemEntity savedEntity = entityManager.persist(itemEntity);
        entityManager.flush();

        Optional<ItemEntity> returnedEntity = itemRepository.findById(savedEntity.getId());

        assertThat(returnedEntity.get().getName())
                .isEqualTo(savedEntity.getName());
    }
    @Test
    public void addItemInventory_thenReturn() {
        int itemInventoryCount = 100;

        ItemEntity itemEntity = new ItemEntity("test name", "test description", 55);
        ItemEntity savedEntity = entityManager.persist(itemEntity);
        entityManager.flush();

        ItemInventoryEntity itemInventoryEntity = new ItemInventoryEntity();
        itemInventoryEntity.setItemEntity(itemEntity);
        itemInventoryEntity.setTotalAvailable(itemInventoryCount);
        entityManager.persist(itemInventoryEntity);
        entityManager.flush();

        Optional<ItemInventoryEntity> returnedItemInventoryEntity = itemInventoryRepository.getItemInventoryByItemId(savedEntity.getId());

        assertThat(returnedItemInventoryEntity.get().getTotalAvailable())
                .isEqualTo(itemInventoryCount);
    }
    @Test
    public void addViewLog_thenReturnCount() {
        int totalViews = 100;
        ItemEntity itemEntity = new ItemEntity("test name", "test description", 55);
        ItemEntity savedEntity = entityManager.persist(itemEntity);
        entityManager.flush();

        for (int i = 0; i < totalViews; i++) {
            ViewLogEntity viewLogEntity = new ViewLogEntity();
            viewLogEntity.setItemEntity(savedEntity);
            viewLogEntity.setViewTime(LocalDateTime.now());
            entityManager.persist(viewLogEntity);

        }
        entityManager.flush();

        LocalDateTime oneHourAgo = LocalDateTime.now().minus(1, ChronoUnit.HOURS);
        int hourlyCount = viewLogRepository.getHourlyCount(savedEntity.getId(), oneHourAgo);

        assertThat(totalViews)
                .isEqualTo(hourlyCount);
    }
}
