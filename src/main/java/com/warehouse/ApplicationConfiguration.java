package com.warehouse;

import com.warehouse.dao.IEditableRepository;
import com.warehouse.dao.IRepository;
import com.warehouse.dao.mysql.EditableRepository;
import com.warehouse.dao.mysql.Repository;
import com.warehouse.dao.mysql.TransactionsRepository;
import com.warehouse.models.*;
import com.warehouse.utils.HibernateUtils;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.warehouse")
public class ApplicationConfiguration {
    @Bean
    public IRepository<Company> createCompanyRepository(){
        return new Repository(Company.class);
    }

    @Bean
    public TransactionsRepository createTransactionsRepository(){
        return new TransactionsRepository();
    }

    @Bean
    public IEditableRepository<Contact> createContactEditableRepository(){
        return new EditableRepository(Contact.class);
    }

    @Bean
    public IEditableRepository<Item> createItemEditableRepository(){
        return new EditableRepository(Item.class);
    }

    @Bean
    public IEditableRepository<ProductCategory> createProductCategoryEditableRepository(){
        return new EditableRepository(ProductCategory.class);
    }

    @Bean
    public IEditableRepository<ProductOwner> createProductOwnerEditableRepository(){
        return new EditableRepository(ProductOwner.class);
    }

    @Bean
    public SessionFactory createSessionFactory(){
        return HibernateUtils.getSessionFactory();
    }
}
