package org.example.config;

import org.example.dao.ProductDAO;
import org.example.dao.DaoFactory;
import org.example.dao.impl.BookDAO;
import org.example.dao.impl.MagazineDAO;
import org.example.dao.impl.BrochureDAO;
import org.example.entity.Product;
import org.example.entity.Book;
import org.example.entity.Magazine;
import org.example.entity.Brochure;

public enum DaoFactoryImpl implements DaoFactory {
    INSTANCE;

    @SuppressWarnings("unchecked")
    @Override
    public <A extends Product<A>> ProductDAO<A> getProductDAO(Class<A> productClass) {
        if (Book.class.equals(productClass)) {
            return (ProductDAO<A>) new BookDAO("books.csv");
        }
        if (Magazine.class.equals(productClass)) {
            return (ProductDAO<A>) new MagazineDAO("magazines.csv");
        }
        if (Brochure.class.equals(productClass)) {
            return (ProductDAO<A>) new BrochureDAO("brochures.csv");
        }
        return null;
    }
}
