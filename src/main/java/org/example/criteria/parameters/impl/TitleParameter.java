package org.example.criteria.parameters.impl;

import org.example.criteria.parameters.Parameter;
import org.example.entity.Product;


public record TitleParameter<A extends Product<A>>(String title) implements Parameter<A> {

    @Override
    public boolean test(A product) {
        return title.equals(product.getTitle());
    }
}



