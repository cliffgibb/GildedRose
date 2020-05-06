package com.miwtech.gildedrose.controller;

import com.miwtech.gildedrose.customresponse.CustomResponseMessage;
import com.miwtech.gildedrose.customresponse.CustomResponseMessageFactory;
import com.miwtech.gildedrose.exception.InvalidItemException;
import com.miwtech.gildedrose.exception.InvalidItemPageException;
import com.miwtech.gildedrose.exception.ItemNotAvailableException;
import com.miwtech.gildedrose.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/***
 * The API Controller provides the public endpoint supported by The Gilded Rose:
 *  - The '/inventory' endpoint provides a list of available items
 *  - The '/purchase' endpoint allows the purchase of an item, and decrements the number of available items.
 */
@RestController
public class APIController {
    private final ItemService itemService;
    private final CustomResponseMessageFactory customResponseMessageFactory;

    public APIController(ItemService itemService, CustomResponseMessageFactory customResponseMessageFactory) {
        this.itemService = itemService;
        this.customResponseMessageFactory = customResponseMessageFactory;
    }
    /***
     * Return a paginated list of items that only includes items that are in stock.
     * @param page
     * @return
     */
    @GetMapping({"/inventory", "/inventory/{page}"})
    public Page getItemInventory(@PathVariable Optional<Integer> page) throws InvalidItemPageException {
        //If the page number is not provided, default to display the first page.
        int displayPage = page.isPresent()?page.get():1;
        return itemService.getItemInventory(displayPage);
    }
    @GetMapping({"/purchase/{itemId}"})
    public CustomResponseMessage purchaseItem(@PathVariable Long itemId) throws ItemNotAvailableException, InvalidItemException {
        itemService.purchaseItem(itemId);

        return customResponseMessageFactory.getSuccessfulPurchaseResponse();
    }
}