package org.example.config;

import org.example.service.ProductService;
import org.example.service.ProductServiceImpl;

public enum ServiceFactory {
    INSTANCE;


    public ProductService getProductService() {

        return new ProductServiceImpl(DaoFactoryImpl.INSTANCE);
    }
}
