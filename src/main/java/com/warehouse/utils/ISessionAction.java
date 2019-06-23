package com.warehouse.utils;

import org.hibernate.Session;

@FunctionalInterface
public interface ISessionAction {
    void invoke(Session session);
}

