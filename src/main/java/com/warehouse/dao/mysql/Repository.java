package com.warehouse.dao.mysql;

import com.warehouse.dao.IRepository;
import com.warehouse.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class Repository<T> implements IRepository<T> {
    protected Session session;
    private Class<T> tClass;
    private String tableName;

    public Repository(Class<T> tClass){
        this.session = HibernateUtils.getSessionFactory().openSession();
        this.tClass = tClass;
        this.tableName = tClass.getSimpleName();
    }

    public T getById(int id){
        return (T)this.session.get(this.tClass, id);
    }

    @Override
    public List<T> getAll() {
        List<T> result = null;

        this.session.beginTransaction();
        result = this.session.createQuery("from " + this.tableName).list();
        this.session.getTransaction().commit();

        return result;
    }

    @Override
    public void add(T entity) {
        this.session.beginTransaction();
        this.session.persist(entity);
        this.session.evict(entity);
        this.session.getTransaction().commit();
    }

    @Override
    public void remove(T entity) {
        this.session.beginTransaction();
        Object mergedEntity = this.session.merge(entity);
        this.session.delete(mergedEntity);
        this.session.getTransaction().commit();
    }
}

