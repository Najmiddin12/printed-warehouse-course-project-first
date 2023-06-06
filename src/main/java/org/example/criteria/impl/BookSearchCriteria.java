package org.example.criteria.impl;

import org.example.criteria.AbstractCriteria;
import org.example.entity.Book;

public class BookSearchCriteria extends AbstractCriteria<Book> {

    @Override
    public Class<Book> getProductType() {
        return Book.class;
    }
}