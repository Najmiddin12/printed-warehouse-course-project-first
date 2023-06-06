package org.example.criteria.parameters.impl;

import org.example.criteria.parameters.Parameter;
import org.example.entity.Range;
import org.example.entity.Book;

public record EditionParameter(Range<Integer> edition) implements Parameter<Book> {

    @Override
    public boolean test(Book product) {
        return edition.isIn(product.getEdition());
    }
}
