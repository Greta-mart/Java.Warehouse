package com.warehouse.controllers;

import com.warehouse.dao.IEditableRepository;
import com.warehouse.models.Contact;
import com.warehouse.utils.OperationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/contacts")
@RestController
public class ContactController extends Controller {
    @Autowired
    private IEditableRepository<Contact> repository;

    public ContactController(){

    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteContact(@RequestBody Contact contact) {
        this.repository.remove(contact);
        return OperationResult.createSuccess();
    }
}
