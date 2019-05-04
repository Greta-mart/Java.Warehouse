package com.warehouse.dao;

public interface IEditableRepository<T> extends IRepository<T>{
    void edit(T entity);
}
