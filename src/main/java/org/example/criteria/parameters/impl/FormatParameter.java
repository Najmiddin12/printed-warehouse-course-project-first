package org.example.criteria.parameters.impl;

import org.example.criteria.parameters.Parameter;
import org.example.entity.Book;

public record FormatParameter(String format) implements Parameter<Book> {

    @Override
    public boolean test(Book product) {
        return format.equals(product.getFormat());
    }
}

