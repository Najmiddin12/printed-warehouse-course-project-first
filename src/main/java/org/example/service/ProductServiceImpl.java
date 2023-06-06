package org.example.service;

import org.example.criteria.SearchCriteria;
import org.example.dao.DaoFactory;
import org.example.entity.Product;
import java.util.Collection;

public class ProductServiceImpl implements ProductService {


    private final DaoFactory factory;

    public ProductServiceImpl(DaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public <A extends Product<A>> Collection<A> find(SearchCriteria<A> criteria) {
        var dao = factory.getProductDAO(criteria.getProductType());
        if (dao == null) {
            throw new IllegalStateException("No DAO is found for " + criteria.getProductType());
        }
        return dao.find(criteria);
    }
}

