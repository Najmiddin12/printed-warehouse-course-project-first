package org.example.criteria.parameters.impl;

import org.example.criteria.parameters.Parameter;
import org.example.entity.Product;


public record PublisherParameter<A extends Product<A>>(String publisher) implements Parameter<A> {

    @Override
    public boolean test(A product) {
        return publisher.equals(product.getPublisher());
    }
}
