package com.warehouse.controllers;

import com.warehouse.dao.IRepository;
import com.warehouse.dao.mysql.Repository;
import com.warehouse.models.ProductCategory;
import com.warehouse.utils.OperationResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/productCategory")
@RestController
public class ProductCategoryController {
    private IRepository<ProductCategory> repository;

    public ProductCategoryController(){
        this.repository = new Repository<ProductCategory>(ProductCategory.class);
    }

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Object index() {
        try
        {
            List<ProductCategory> categories = this.repository.getAll();
            return OperationResult.CreateSuccess(categories);
        }
        catch (Exception ex)
        {
            return OperationResult.CreateError(ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Object addProductCategory(@RequestBody ProductCategory productCategory) {
        try
        {
            this.repository.add(productCategory);
            return OperationResult.CreateSuccess(null);
        }
        catch (Exception ex)
        {
            return OperationResult.CreateError(ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Object deleteProductCategory(@RequestBody ProductCategory productCategory) {
        try
        {
            this.repository.remove(productCategory);
            return OperationResult.CreateSuccess(null);
        }
        catch (Exception ex)
        {
            return OperationResult.CreateError(ex.getMessage());
        }
    }
}
