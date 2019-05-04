package com.warehouse.controllers;

import com.warehouse.dao.IRepository;
import com.warehouse.dao.mysql.Repository;
import com.warehouse.models.Company;
import com.warehouse.utils.OperationResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/company")
@RestController
public class CompanyController {
    private IRepository<Company> repository;

    public CompanyController(){
        this.repository = new Repository<Company>(Company.class);
    }

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody Object index() {
        try
        {
            List<Company> companies = this.repository.getAll();
            return OperationResult.CreateSuccess(companies);
        }
        catch (Exception ex)
        {
            return OperationResult.CreateError(ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Object addCompany(@RequestBody Company company) {
        try
        {
            this.repository.add(company);
            return OperationResult.CreateSuccess(null);
        }
        catch (Exception ex)
        {
            return OperationResult.CreateError(ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Object deleteCompany(@RequestBody Company company) {
        try
        {
            this.repository.remove(company);
            return OperationResult.CreateSuccess(null);
        }
        catch (Exception ex)
        {
            return OperationResult.CreateError(ex.getMessage());
        }
    }
}
