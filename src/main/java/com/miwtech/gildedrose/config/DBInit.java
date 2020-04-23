package com.miwtech.gildedrose.config;

import com.miwtech.gildedrose.entity.ItemEntity;
import com.miwtech.gildedrose.entity.ItemInventoryEntity;
import com.miwtech.gildedrose.entity.UserEntity;
import com.miwtech.gildedrose.repository.ItemInventoryRepository;
import com.miwtech.gildedrose.repository.ItemRepository;
import com.miwtech.gildedrose.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Load the sample data into the in-memory database.
 */
@Component
public class DBInit implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(DBInit.class);
    private static final int defaultInventoryTotalPerItem  = 10;
    private final ItemRepository itemRepository;
    private final ItemInventoryRepository itemInventoryRepository;
    private final UserRepository userRepository;

    public DBInit(ItemRepository itemRepository, ItemInventoryRepository itemInventoryRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.itemInventoryRepository = itemInventoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.debug("Init the DB with 39 Items and set the initial number available to be 10 per item");
        ItemEntity[] items= initItems();
        initItemInventoryCounts(items);
        initUsers();
    }

    /**
     * Add 39 sample Items to the repository
     * @return
     */
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
                new ItemEntity("Mud Scrub Soap", "Bush Smart's Mud Scrub is part of their \"Man Soap\" collection. The Mud Scrub soap is made from patchouli essential oil, purifying mud, and all natural vegetable ingredients", 15),
                new ItemEntity("Whitney Pullover", "Buttons are fussy. Sometimes you just want to roll out of bed, put on the pull over and get to the days work.", 138),
                new ItemEntity("Gertrude Cardigan", "There's a certain type of Cardigan that' common in the old quays of Dublin and the wind battered coasts throughout Britain. We'e brought this old world flare across the pond.", 108),
                new ItemEntity("Harriet Chambray", "100% Organic Cotton Chambray, 4.9 oz Fabric", 98),
                new ItemEntity("Derby Tier Backpack", "Our Derby backpack is built with downpour proof sustainable canvas complemented by leather trim and brass hardware. Features padded canvas shoulder straps adjustable for all -day comfort. Finished with a drawstring closure and snap buckles to keep your belongings safe and secure on any trail taken.", 148),
                new ItemEntity("Chevron", "100% organic cotton, stone washed slub knit 6 oz jersey fabric", 36),
                new ItemEntity("Guaranteed", "100% organic cotton, stone washed slub knit 6 oz jersey fabric", 36),
                new ItemEntity("Moon Cycle", "Julie is 5'8\" and wearing a size small", 36),
                new ItemEntity("5 Panel Camp Cap", "A classic 5 panel hat with our United By Blue logo on the front and an adjustable strap to keep fit and secure. Made with recycled polyester and organic cotton mix.", 48),
                new ItemEntity("Dawson Trolley", "It's a duffle. It's a wheeling suitcase. It's both. This versatile, carryon-sized travel companion has a contrast leather trim and rugged brass hardware, a removable long strap and stash pockets on the inside and outside of the bag. When not wheeling it around, easily store the collapsible handle behind a hidden zipper pocket.", 278),
                new ItemEntity("Canvas Lunch Bag", "Reusable, durable, and roomy. Our lunchbag has a snap-closure top that rolls down to perfect for trail snacks and lunches. Small enough to throw in any of our bags.", 32),
                new ItemEntity("Duckworth Woolfill Jacket", "Inspired by the timeless, functional style of your grandfather's work coat, the Foraker features brass buttons and 4 patch pockets. Crafted in Bristol, Tennessee, our 10oz organic duck canvas is light enough for an early summer morning, but rugged enough to handle your days work.", 188),
                new ItemEntity("Scout Backpack", "This durable backpack is ready for any adventure, large or small. Features adjustable and padded shoulder pads for comfort. Designed with a storm flap and a secured by two snap-button closure. Made with a waxed downpour proof exterior canvas and a soft cotton interior lining. Finished with brass hardware and genuine leather trimmings.", 128),
                new ItemEntity("Cydney Plaid", "Super soft, super responsible - our wovens were made to last.", 98),
                new ItemEntity("Red Wing Iron Ranger Boot", "The Mesabi Iron Range lies in northern Minnesota, a rugged and remote area known for its iron mines. The local residents who work these mines are proudly known as Iron Rangers, individuals with a sense of adventure and a determined personality. Originally designed to be worn in the iron mines, Iron Ranger work boots had to be as tough as the people who wore them in demanding conditions. Iron Ranger boots are built with a double layer of leaver over the toe to provide an extra measure of safety.", 310),
                new ItemEntity("Long Sleeve Swing Shirt", "We updated our standard with a relaxed fit that stands up to constant wear.", 46),
                new ItemEntity("Mola Headlamp", "The Mola Headlamp is a powerful, innovative new LED lamp that follows the angle of your eyes as you look up and down, eliminating the need for clunky hinges.", 45),
                new ItemEntity("Double Wall Mug", "Snow Peaks classic insulated stainless steel mug is perfect for the home or campsite.", 24),
                new ItemEntity("The Field Report Vol. 2", "Produced quarterly by United By Blue, The Field Report is a two-page printed newsprint of exciting upcoming product arrivals, updates on our ocean and waterway cleanups, behind the scenes, and accompanying beautiful imagery.", 0),
                new ItemEntity("Camp Stool", "The Camp Stool is a classic take-anywhere seat that folds flat for easy transport and storage. The stool is crafted in Western North Carolina of kiln dried American Ash hardwood that has been finished with a protective Danish oil. The base of the seat is made from our classic navy blue organic cotton canvas, the same we use in products such as our Foraker Coat,  that is used to make the back of the chair, and detailed with a United By Blue woven patch. This stool is part of a collaboration United By Blue has with heritage outdoor furniture maker Blue Ridge Chair Works.", 78),
                new ItemEntity("Hudderton Backpack", "Durable, rugged, and dependable designed with four zipper compartments. Two bellowed front pockets allow for easy access to smaller items, one large, spacious compartment with a padded laptop sleeve, and a tiny convenient pouch on top to keep keys and other small items secure. The Hudderton is built with organic downpour proof canvas, a durable full grain leather bottom to prevent wear and tear, and padded canvas shoulder straps for all-day comfort. From the commute to the trail the Hudderton is perfect for bag for the entire week.", 98),
        };

        itemRepository.saveAll(Arrays.asList(items));
        return items;
    }

    /**
     * Initialize the inventory count for each item; 10 available for each item
     * @param items
     */
    private void initItemInventoryCounts(ItemEntity[] items) {
        List<ItemInventoryEntity> itemInventoryEntityList = new ArrayList<>();
        for(ItemEntity itemEntity : items) {
            ItemInventoryEntity itemInventoryEntity = new ItemInventoryEntity();
            itemInventoryEntity.setItemEntity(itemEntity);
            itemInventoryEntity.setTotalAvailable(defaultInventoryTotalPerItem);
            itemInventoryEntityList.add(itemInventoryEntity);
        }
        itemInventoryRepository.saveAll(itemInventoryEntityList);
    }

    private void initUsers() {
        UserEntity userEntity = new UserEntity("testuser", "securepassword", "USER");
        userRepository.save(userEntity);
    }
}