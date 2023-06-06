package org.example.dao;

import org.example.entity.Product;

public interface DaoFactory {

    public <A extends Product<A>> ProductDAO<A> getProductDAO(Class<A> applianceClass);
}