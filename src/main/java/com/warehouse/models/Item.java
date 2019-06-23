package com.warehouse.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "item", schema = "warehouse")
public class Item {
    private int id;
    private String title;
    private int count;
    private double price;
    private Timestamp storingDate;
    private Set<ProductCategory> categories;
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
    @Column(name = "title", nullable = true, length = 50)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "count", nullable = false)
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 2)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "storing_date", nullable = false)
    public Timestamp getStoringDate() {
        return storingDate;
    }

    public void setStoringDate(Timestamp storingDate) {
        this.storingDate = storingDate;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "item_product_category",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "product_category_id"))
    public Set<ProductCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<ProductCategory> categories) {
        this.categories = categories;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_owner_id", referencedColumnName = "id", nullable = false)
    public ProductOwner getProductOwner() {
        return productOwner;
    }

    public void setProductOwner(ProductOwner productOwner) {
        this.productOwner = productOwner;
    }

    public double calculateCost(){
        return this.getPrice() * this.getCount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (id != item.id) return false;
        if (count != item.count) return false;
        if (Double.compare(item.price, price) != 0) return false;
        if (title != null ? !title.equals(item.title) : item.title != null) return false;
        if (storingDate != null ? !storingDate.equals(item.storingDate) : item.storingDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + count;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (storingDate != null ? storingDate.hashCode() : 0);
        return result;
    }
}
