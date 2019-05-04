package com.warehouse.dao.mysql;

import com.warehouse.dao.IEditableRepository;

public class EditableRepository<T> extends Repository<T> implements IEditableRepository<T> {

    public EditableRepository(Class<T> tClass) {
        super(tClass);
    }

    @Override
    public void edit(T entity) {
        this.session.beginTransaction();
        Object mergedEntity = this.session.merge(entity);
        this.session.evict(mergedEntity);
        this.session.getTransaction().commit();
    }
}
