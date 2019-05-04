package com.warehouse.dao;

import java.util.List;

public interface IRepository<T>{
    T getById(int id);
    List<T> getAll();
    void add(T entity);
    void remove(T entity);
}

