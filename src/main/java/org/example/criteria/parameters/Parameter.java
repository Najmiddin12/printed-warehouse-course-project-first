package org.example.criteria.parameters;

import org.example.entity.Product;

public interface Parameter<A extends Product<A>> {

    boolean test(A product);

    static <A extends Product<A>> Parameter<A> any() {
        return product -> true;
    }

    static <A extends Product<A>> Parameter<A> none() {
        return product -> false;
    }

}