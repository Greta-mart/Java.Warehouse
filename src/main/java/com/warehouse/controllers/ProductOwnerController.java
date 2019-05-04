package com.warehouse.controllers;

import com.warehouse.dao.IEditableRepository;
import com.warehouse.dao.mysql.EditableRepository;
import com.warehouse.models.Contact;
import com.warehouse.models.ProductOwner;
import com.warehouse.utils.OperationResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/productOwner")
@RestController
public class ProductOwnerController {
    private IEditableRepository<ProductOwner> productOwnerRepository;
    private IEditableRepository<Contact> contactRepository;

    public ProductOwnerController(){
        this.productOwnerRepository = new EditableRepository<ProductOwner>(ProductOwner.class);
        this.contactRepository = new EditableRepository<Contact>(Contact.class);
    }

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody Object index() {
        try
        {
            List<ProductOwner> productOwners = this.productOwnerRepository.getAll();
            return OperationResult.CreateSuccess(productOwners);
        }
        catch (Exception ex)
        {
            return OperationResult.CreateError(ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Object addProductOwner(@RequestBody ProductOwner productOwner) {
        try
        {
            this.productOwnerRepository.add(productOwner);

            List<Contact> contacts = productOwner.getContacts();

            if (contacts != null){
                for (Contact contact : contacts) {
                    contact.setProductOwner(productOwner);
                    this.contactRepository.add(contact);
                }
            }

            return OperationResult.CreateSuccess(null);
        }
        catch (Exception ex)
        {
            return OperationResult.CreateError(ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Object editProductOwner(@RequestBody ProductOwner productOwner) {
        try
        {
            this.productOwnerRepository.edit(productOwner);

            List<Contact> contacts = productOwner.getContacts();

            if (contacts != null){
                for (Contact contact : contacts) {
                    contact.setProductOwner(productOwner);
                    this.contactRepository.edit(contact);
                }
            }

            return OperationResult.CreateSuccess(null);
        }
        catch (Exception ex)
        {
            return OperationResult.CreateError(ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Object deleteProductOwner(@RequestBody ProductOwner productOwner) {
        try
        {
            ProductOwner existsProductOwner = this.productOwnerRepository.getById(productOwner.getId());
            if (existsProductOwner.getItems().size() > 0)
                throw new IllegalStateException("Can't remove product owner. Reason: there are some items, which has relation to this product owner");

            List<Contact> contacts = existsProductOwner.getContacts();
            for (Contact contact: contacts) {
                this.contactRepository.remove(contact);
            }

            this.productOwnerRepository.remove(productOwner);
            return OperationResult.CreateSuccess(null);
        }
        catch (Exception ex)
        {
            return OperationResult.CreateError(ex.getMessage());
        }
    }
}
