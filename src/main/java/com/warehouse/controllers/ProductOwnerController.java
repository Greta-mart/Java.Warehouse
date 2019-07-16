package com.warehouse.controllers;

import com.warehouse.dao.IEditableRepository;
import com.warehouse.models.Contact;
import com.warehouse.models.ProductOwner;
import com.warehouse.utils.OperationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/productOwners")
@RestController
public class ProductOwnerController extends Controller {
    @Autowired
    private IEditableRepository<ProductOwner> productOwnerRepository;

    @Autowired
    private IEditableRepository<Contact> contactRepository;

    public ProductOwnerController(){
    }

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object index() {
        List<ProductOwner> productOwners = this.productOwnerRepository.getAll();
        return OperationResult.createSuccess(productOwners);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addProductOwner(@RequestBody ProductOwner productOwner) {
        this.productOwnerRepository.add(productOwner);

        List<Contact> contacts = productOwner.getContacts();

        if (contacts != null){
            for (Contact contact : contacts) {
                contact.setProductOwner(productOwner);
                this.contactRepository.add(contact);
            }
        }

        return OperationResult.createSuccess();
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object editProductOwner(@RequestBody ProductOwner productOwner) {
        this.productOwnerRepository.edit(productOwner);

        List<Contact> contacts = productOwner.getContacts();

        if (contacts != null){
            for (Contact contact : contacts) {
                contact.setProductOwner(productOwner);
                this.contactRepository.edit(contact);
            }
        }

        return OperationResult.createSuccess();
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteProductOwner(@RequestBody ProductOwner productOwner) {
        ProductOwner existsProductOwner = this.productOwnerRepository.getById(productOwner.getId());
        if (existsProductOwner.getItems().size() > 0)
            throw new IllegalStateException("Can't remove product owner. Reason: there are some items, which has relation to this product owner");

        List<Contact> contacts = existsProductOwner.getContacts();
        for (Contact contact: contacts) {
            this.contactRepository.remove(contact);
        }

        this.productOwnerRepository.remove(productOwner);

        return OperationResult.createSuccess();
    }

    @RequestMapping(path ="/top5", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getTop(){
        List<ProductOwner> all = this.productOwnerRepository.getAll();

        all.stream().forEach(p -> p.calculateTotalCost());
        List<ProductOwner> result = all.stream()
                .sorted(Comparator.comparingDouble(ProductOwner::getTotalCost).reversed())
                .limit(5)
                .collect(Collectors.toList());

        return OperationResult.createSuccess(result);
    }
}
