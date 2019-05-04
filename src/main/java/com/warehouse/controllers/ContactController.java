package com.warehouse.controllers;

import com.warehouse.dao.IRepository;
import com.warehouse.dao.mysql.Repository;
import com.warehouse.models.Contact;
import com.warehouse.utils.OperationResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/contact")
@RestController
public class ContactController {
    private IRepository<Contact> repository;

    public ContactController(){
        this.repository = new Repository<Contact>(Contact.class);
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Object deleteContact(@RequestBody Contact contact) {
        try
        {
            this.repository.remove(contact);
            return OperationResult.CreateSuccess(null);
        }
        catch (Exception ex)
        {
            return OperationResult.CreateError(ex.getMessage());
        }
    }
}
