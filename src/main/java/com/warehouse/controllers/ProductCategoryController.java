package com.warehouse.controllers;

import com.warehouse.dao.IEditableRepository;
import com.warehouse.models.ProductCategory;
import com.warehouse.utils.OperationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/productCategories")
@RestController
public class ProductCategoryController extends Controller {
    @Autowired
    private IEditableRepository<ProductCategory> repository;

    public ProductCategoryController(){

    }

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object index() {
        List<ProductCategory> categories = this.repository.getAll();
        return OperationResult.createSuccess(categories);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addProductCategory(@RequestBody ProductCategory productCategory) {
        this.repository.add(productCategory);
        return OperationResult.createSuccess();
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteProductCategory(@RequestBody ProductCategory productCategory) {
        this.repository.remove(productCategory);
        return OperationResult.createSuccess();
    }
}
