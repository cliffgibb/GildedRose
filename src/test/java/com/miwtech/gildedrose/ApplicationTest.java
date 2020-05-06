package com.miwtech.gildedrose;

import com.miwtech.gildedrose.entity.ItemEntity;
import com.miwtech.gildedrose.entity.ItemInventoryEntity;
import com.miwtech.gildedrose.exception.InvalidItemException;
import com.miwtech.gildedrose.exception.InvalidItemPageException;
import com.miwtech.gildedrose.exception.ItemNotAvailableException;
import com.miwtech.gildedrose.repository.ItemInventoryRepository;
import com.miwtech.gildedrose.repository.ItemRepository;
import com.miwtech.gildedrose.repository.ViewLogRepository;
import com.miwtech.gildedrose.service.ItemService;
import com.miwtech.gildedrose.service.SurgePricingService;
import com.miwtech.gildedrose.service.ViewLogService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
public class ApplicationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemInventoryRepository itemInventoryRepository;
    @Autowired
    private ViewLogRepository viewLogRepository;

    private ItemService itemService;

    @Before
    public void setUp()  {
        ItemEntity[] testItems = initItems();
        for(ItemEntity itemEntity : testItems) {
            entityManager.persistAndFlush(itemEntity);
            ItemInventoryEntity itemInventoryEntity = new ItemInventoryEntity();
            itemInventoryEntity.setItemEntity(itemEntity);
            itemInventoryEntity.setTotalAvailable(10);
            entityManager.persistAndFlush(itemInventoryEntity);
        }

        ViewLogService viewLogService = new ViewLogService(viewLogRepository);
        SurgePricingService surgePricingService = new SurgePricingService(viewLogService);
        ReflectionTestUtils.setField(surgePricingService, "surgePriceFactor", new BigDecimal(1.1));
        ReflectionTestUtils.setField(surgePricingService, "surgePricingThreshold", 10);
        itemService = new ItemService(itemRepository, itemInventoryRepository, viewLogService, surgePricingService);
        ReflectionTestUtils.setField(itemService, "itemsPerPage", 10);
    }

    @Test
    @Order(1)
    public void testPriceWithoutSurge() throws InvalidItemPageException {
        Page<ItemEntity> itemEntitiesPage = itemService.getItemInventory(1);

        BigDecimal expectedPrice = new BigDecimal(9).setScale(2, RoundingMode.HALF_UP);
        ItemEntity firstEntity = itemEntitiesPage.getContent().get(0);
        Assert.assertThat(firstEntity.getPrice(), Matchers.comparesEqualTo(expectedPrice));
    }

    @Test
    @Order(2)
    public void testPriceAfterSurge() throws InvalidItemPageException {
        for (int i = 0; i<10; i++) {
            Page<ItemEntity> simulatedViewPage = itemService.getItemInventory(1);
        }
        Page<ItemEntity> itemEntitiesPageAfter10Calls = itemService.getItemInventory(1);
        BigDecimal expectedPrice = new BigDecimal(9.9).setScale(2, RoundingMode.HALF_UP);
        ItemEntity firstEntity = itemEntitiesPageAfter10Calls.getContent().get(0);
        Assert.assertThat(firstEntity.getPrice(), Matchers.comparesEqualTo(expectedPrice));
    }

    @Order(3)
    @Test(expected = ItemNotAvailableException.class)
    public void testPurchaseOutOfInventory() throws ItemNotAvailableException, InvalidItemException, InvalidItemPageException {
        Page<ItemEntity> itemEntitiesPage = itemService.getItemInventory(1);
        List<ItemEntity> itemEntities = itemEntitiesPage.getContent();
        ItemEntity firstItemEntity = itemEntities.get(0);
        //Purchase all 10 items
        for (int i = 0; i<10; i++) {
            itemService.purchaseItem(firstItemEntity.getId());
        }
        //attempt to purchase the 11th item (not in inventory)
        itemService.purchaseItem(firstItemEntity.getId());

    }
    private ItemEntity[] initItems() {
        ItemEntity[] items = {
                new ItemEntity("Scented Candle", "A lavender scented candle", 9),
                new ItemEntity("Hand Soap", "Organic oatmeal hand soap", 4),
                new ItemEntity("Sleep mask", "A sleep mask with a gel pack for heating or cooling", 30),
                new ItemEntity("Flowers", "A dozen roses", 33),
                new ItemEntity("Pillow Cases", "Egyptian cotton pillow cases", 45),
                new ItemEntity("Bath Salts", "A bag of bath salts", 8),
                new ItemEntity("Book of poems", "A collection of romantic poetry", 15),
                new ItemEntity("Sandals", "Sandals with gel soles", 22),
                new ItemEntity("Bath robe", "Organic cotton bath robe", 52),
                new ItemEntity("Adult coloring book", "Coloring book for adults", 12),
                new ItemEntity("Chocolate", "Belgium chocolate", 7),
                new ItemEntity("Luggage", "Lightweight luggage", 40),
                new ItemEntity("Coffee", "Single-sourced organic coffee", 12),
                new ItemEntity("Map", "Map of the local area", 3),
                new ItemEntity("The Scout Skincare Kit", "A collection of the best Ursa Major has to offer! \"The Scout\" kit contains travel sizes of their best selling skin care items.", 36),
                new ItemEntity("Ayres Chambray", "Comfortable and practical, our chambray button down is perfect for travel or days spent on the go. The Ayres Chambray has a rich, washed out indigo color suitable to throw on for any event. Made with sustainable soft chambray featuring two chest pockets with sturdy and scratch resistant corozo buttons.", 98),
                new ItemEntity("Lodge", "The lodge, after a day of white slopes, is a place of revelry, blazing fires and high spirits.", 36),
                new ItemEntity("Pennsylvania Notebooks", "These Pennsylvania themed notebooks are part of the \"County Fair Edition\" Field Notes. With this 3-Pack, we pay homage to United By Blue's home state of PA. The colors of the notebooks are the colors of 1st, 2nd and 3rd place County Fair ribbons; blue, red and yellow. They’re printed on 100-lb. linen cover stock and all three feature metallic gold printing and 48 pages of blank graph paper with light blue/grey lines inside. The back covers feature a bevy of meticulously-researched state facts and figures.", 10),
                new ItemEntity("Mud Scrub Soap", "Bush Smart's Mud Scrub is part of their \"Man Soap\" collection. The Mud Scrub soap is made from patchouli essential oil, purifying mud, and all natural vegetable ingredients", 15)
        };

        return items;
    }
}
