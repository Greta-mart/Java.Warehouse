package com.warehouse.controllers;

import com.warehouse.dao.IEditableRepository;
import com.warehouse.dao.mysql.EditableRepository;
import com.warehouse.models.Item;
import com.warehouse.models.ProductCategory;
import com.warehouse.utils.OperationResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@RequestMapping("/api/v1/item")
@RestController
public class ItemController {
    private IEditableRepository<Item> itemRepository;
    private IEditableRepository<ProductCategory> productCategoryRepository;

    public ItemController(){
        this.itemRepository = new EditableRepository<Item>(Item.class);
        this.productCategoryRepository = new EditableRepository<ProductCategory>(ProductCategory.class);
    }

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody Object index() {
        try
        {
            List<Item> items = this.itemRepository.getAll();
            return OperationResult.CreateSuccess(items);
        }
        catch (Exception ex)
        {
            return OperationResult.CreateError(ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Object addItem(@RequestBody Item item) {
        try
        {
            item.setStoringDate(new Timestamp(System.currentTimeMillis()));

            Set<ProductCategory> categories = item.getCategories();

            for (ProductCategory category : categories) {
                ProductCategory existsCategory = this.productCategoryRepository.getById(category.getId());
                existsCategory.addItem(item);

                this.productCategoryRepository.edit(existsCategory);
            }

            this.itemRepository.add(item);
            return OperationResult.CreateSuccess(null);
        }
        catch (Exception ex)
        {
            return OperationResult.CreateError(ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Object withdrawItem(@RequestBody Item item) {
        try
        {
            Item existsItem = this.itemRepository.getById(item.getId());

            int newCount = existsItem.getCount() - item.getCount();

            if (newCount < 0)
                throw new IllegalStateException("Not enough count for withdraw");

            if (newCount == 0)
                this.itemRepository.remove(item);
            else{
                existsItem.setCount(newCount);
                this.itemRepository.edit(existsItem);
            }

            return OperationResult.CreateSuccess(null);
        }
        catch (Exception ex)
        {
            return OperationResult.CreateError(ex.getMessage());
        }
    }
}
