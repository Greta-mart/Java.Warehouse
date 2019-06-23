package com.warehouse.utils;

import org.hibernate.Session;

@FunctionalInterface
public interface ISessionFunc<T> {
    T invoke(Session session);
}
