package com.miwtech.gildedrose.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.miwtech.gildedrose.customresponse.CustomResponseMessage;
import com.miwtech.gildedrose.customresponse.CustomResponseMessageFactory;
import com.miwtech.gildedrose.exception.InvalidItemException;
import com.miwtech.gildedrose.exception.InvalidItemPageException;
import com.miwtech.gildedrose.exception.ItemNotAvailableException;
import com.miwtech.gildedrose.service.ItemService;
import com.miwtech.gildedrose.task.framework.TaskContext;
import com.miwtech.gildedrose.task.framework.TaskExecutionResult;
import com.miwtech.gildedrose.task.framework.TaskStatus;
import com.miwtech.gildedrose.task.framework.exceptions.TaskException;
import com.miwtech.gildedrose.task.tasks.playstore.login.PlayStoreLoginTask;

/***
 * The API Controller provides the public endpoint supported by The Gilded Rose:
 *  - The '/inventory' endpoint provides a list of available items
 *  - The '/purchase' endpoint allows the purchase of an item, and decrements the number of available items.
 */
@RestController
public class StoreController {
    private static final Logger logger = LoggerFactory.getLogger(StoreController.class);
    private final ItemService itemService;
    private final CustomResponseMessageFactory customResponseMessageFactory;

    public StoreController(ItemService itemService,
                           CustomResponseMessageFactory customResponseMessageFactory) {
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
        logger.debug(String.format("Purchased Item %s ",itemId));
        return customResponseMessageFactory.getSuccessfulPurchaseResponse();
    }
    @GetMapping({"/playstore"})
    public CustomResponseMessage playStoreLogin() throws Exception {
        //TaskContext context = TaskContext.builder().deviceId("ABC123").build();

        TaskExecutionResult result = PlayStoreLoginTask.fulfill("ABC123");
        CustomResponseMessage responseMessage;
        if (result.getStatus() == TaskStatus.SUCCESS) {
            responseMessage = new CustomResponseMessage("200", result.getMessage());
        } else {
            responseMessage = new CustomResponseMessage("500", result.getMessage());
        }
        return responseMessage;
    }
    /*
    @ExceptionHandler({ TaskException.class })
    public TaskExecutionResult handleException(TaskException e) {
        TaskExecutionResult taskExecutionResult = new TaskExecutionResult();
        taskExecutionResult.setStatus(TaskStatus.FAILURE);
        taskExecutionResult.setMessage(e.getMessage());
        return taskExecutionResult;
    }

     */

}
