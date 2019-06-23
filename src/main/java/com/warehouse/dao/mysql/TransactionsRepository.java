package com.warehouse.dao.mysql;

import com.warehouse.models.Transaction;

import java.sql.Timestamp;
import java.util.List;

public class TransactionsRepository extends Repository<Transaction> {
    public TransactionsRepository() {
        super(Transaction.class);
    }

    public List<Transaction> getByTime(Timestamp from) {
        String query = String.format("FROM %s t WHERE t.dateTime > '%s'", this.tableName, from.toString());
        List<Transaction> result = this.sessionFunc(s -> s.createQuery(query).list());

        return result;
    }
}
