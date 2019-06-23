package com.warehouse.controllers;

import com.warehouse.dao.IRepository;
import com.warehouse.models.Company;
import com.warehouse.utils.OperationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/companies")
@RestController
public class CompanyController extends Controller {
    @Autowired
    private IRepository<Company> repository;

    public CompanyController(){
    }

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object index() {
        List<Company> companies = this.repository.getAll();
        return OperationResult.CreateSuccess(companies);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addCompany(@RequestBody Company company) {
        this.repository.add(company);
        return OperationResult.CreateSuccess(null);
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteCompany(@RequestBody Company company) {
        this.repository.remove(company);
        return OperationResult.CreateSuccess(null);
    }
}
