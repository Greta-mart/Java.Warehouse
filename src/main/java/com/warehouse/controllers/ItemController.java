package com.warehouse.controllers;

import com.warehouse.dao.IEditableRepository;
import com.warehouse.dao.mysql.TransactionsRepository;
import com.warehouse.models.*;
import com.warehouse.utils.OperationResult;
import com.warehouse.utils.TimestampUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@RequestMapping("/api/v1/items")
@RestController
public class ItemController extends Controller {
    @Autowired
    private IEditableRepository<Item> itemRepository;

    @Autowired
    private IEditableRepository<ProductCategory> productCategoryRepository;

    @Autowired
    private TransactionsRepository transactionRepository;

    private Timestamp getCurrentDateTime(){
        return new Timestamp(System.currentTimeMillis());
    }

    public ItemController(){

    }

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object index() {
        List<Item> items = this.itemRepository.getAll();
        return OperationResult.CreateSuccess(items);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addItem(@RequestBody Item item) {
        item.setStoringDate(this.getCurrentDateTime());

        Set<ProductCategory> categories = item.getCategories();

        for (ProductCategory category : categories) {
            ProductCategory existsCategory = this.productCategoryRepository.getById(category.getId());
            existsCategory.addItem(item);

            this.productCategoryRepository.edit(existsCategory);
        }

        this.itemRepository.add(item);

        this.transactionRepository.add(Transaction.createDeposit(item.getStoringDate(), item, item.getCount()));

        return OperationResult.CreateSuccess(null);
    }

    @RequestMapping(path = "/deposit", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deposit(@RequestBody Item item) {
        Item existsItem = this.itemRepository.getById(item.getId());

        if (existsItem == null)
            throw new IllegalArgumentException("Can't find item with id = " + item.getId());

        int newCount = existsItem.getCount() + item.getCount();

        existsItem.setCount(newCount);
        this.itemRepository.edit(existsItem);

        this.transactionRepository.add(Transaction.createDeposit(this.getCurrentDateTime(), existsItem, item.getCount()));

        return OperationResult.CreateSuccess(null);
    }

    @RequestMapping(path = "/withdraw", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object withdraw(@RequestBody Item item) {
        Item existsItem = this.itemRepository.getById(item.getId());

        if (existsItem == null)
            throw new IllegalArgumentException("Can't find item with id = " + item.getId());

        int newCount = existsItem.getCount() - item.getCount();

        if (newCount < 0)
            throw new IllegalStateException("Not enough count for withdraw");

        existsItem.setCount(newCount);
        this.itemRepository.edit(existsItem);

        this.transactionRepository.add(Transaction.createWithdraw(this.getCurrentDateTime(), existsItem, item.getCount()));

        return OperationResult.CreateSuccess(null);
    }

    @RequestMapping(path = "/statistic/daily", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object daily() {
        Timestamp from = TimestampUtils.addDays(this.getCurrentDateTime(), -1);

        List<Transaction> transactions = this.transactionRepository.getByTime(from);

        TransactionsStatistics statistics = new TransactionsStatistics(transactions);

        return OperationResult.CreateSuccess(statistics);
    }

    @RequestMapping(path = "/statistic/monthly", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object monthly() {
        Timestamp from = TimestampUtils.addMonth(this.getCurrentDateTime(), -1);

        List<Transaction> transactions = this.transactionRepository.getByTime(from);

        TransactionsStatistics statistics = new TransactionsStatistics(transactions);

        return OperationResult.CreateSuccess(statistics);
    }
}
