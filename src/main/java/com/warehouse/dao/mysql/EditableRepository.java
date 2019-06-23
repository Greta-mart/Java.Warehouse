package com.warehouse.dao.mysql;

import com.warehouse.dao.IEditableRepository;

public class EditableRepository<T> extends Repository<T> implements IEditableRepository<T> {

    public EditableRepository(Class<T> tClass) {
        super(tClass);
    }

    @Override
    public void edit(T entity) {
        this.sessionAction(s -> {
            s.merge(entity);
        });
    }
}
