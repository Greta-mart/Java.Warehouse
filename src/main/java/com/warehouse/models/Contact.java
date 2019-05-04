package com.warehouse.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table(name = "contact", schema = "warehouse")
public class Contact {
    private int id;
    private String accessModifier;
    private String type;
    private String value;
    private ProductOwner productOwner;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "access_modifier", nullable = false, length = 20)
    public String getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 20)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "value", nullable = false, length = 50)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @ManyToOne
    @JoinColumn(name = "product_owner_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    public ProductOwner getProductOwner() {
        return this.productOwner;
    }

    public void setProductOwner(ProductOwner productOwner) {
        this.productOwner = productOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (id != contact.id) return false;
        if (accessModifier != null ? !accessModifier.equals(contact.accessModifier) : contact.accessModifier != null)
            return false;
        if (type != null ? !type.equals(contact.type) : contact.type != null) return false;
        if (value != null ? !value.equals(contact.value) : contact.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (accessModifier != null ? accessModifier.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
