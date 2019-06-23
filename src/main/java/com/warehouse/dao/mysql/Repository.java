package com.warehouse.dao.mysql;

import com.warehouse.dao.IRepository;
import com.warehouse.utils.ISessionAction;
import com.warehouse.utils.ISessionFunc;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Repository<T> implements IRepository<T> {
    private Class<T> tClass;
    protected String tableName;

    @Autowired
    private SessionFactory sessionFactory;

    public Repository(Class<T> tClass){
        this.tClass = tClass;
        this.tableName = tClass.getSimpleName();
    }

    public T getById(int id){
        T result = this.sessionFunc(s -> s.get(this.tClass, id));

        return result;
    }

    @Override
    public List<T> getAll() {
        List<T> result = this.sessionFunc(s -> s.createQuery("from " + this.tableName).list());

        return result;
    }

    @Override
    public void add(T entity) {
        this.sessionAction(s -> {
            s.persist(entity);
            s.evict(entity);
        });
    }

    @Override
    public void remove(T entity) {
        this.sessionAction(s -> {
            Object mergedEntity = s.merge(entity);
            s.delete(mergedEntity);
        });
    }

    //region Misc

    protected void sessionAction(ISessionAction action){
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();

        action.invoke(session);

        session.getTransaction().commit();
        session.close();
    }

    protected <TFunc> TFunc sessionFunc(ISessionFunc<TFunc> func){
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();

        TFunc result = func.invoke(session);

        session.getTransaction().commit();
        session.close();

        return result;
    }

    //endregion
}

