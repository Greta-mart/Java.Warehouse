package com.warehouse.models;

import java.sql.Timestamp;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "transaction_history", schema = "warehouse")
public class Transaction {
    private int id;
    private TransactionType type;
    private Timestamp dateTime;
    private int count;

    private Item item;

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
    @Column(name = "type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    public TransactionType getType() {
        return this.type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "date_time", nullable = false)
    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    @Basic
    @Column(name = "count", nullable = false)
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Transaction(){

    }

    public Transaction(TransactionType type, Timestamp dateTime, Item item, int count){
        this.setType(type);
        this.setDateTime(dateTime);
        this.setItem(item);
        this.setCount(count);
    }

    public double CalculateCost(){
        return this.getItem().getPrice() * this.getCount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id &&
                count == that.count &&
                Objects.equals(type, that.type) &&
                Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, dateTime, count);
    }

    public static Transaction createDeposit(Timestamp dateTime, Item item, int count){
        return new Transaction(TransactionType.DEPOSIT, dateTime, item, count);
    }

    public static Transaction createWithdraw(Timestamp dateTime, Item item, int count){
        return new Transaction(TransactionType.WITHDRAW, dateTime, item, count);
    }
}
