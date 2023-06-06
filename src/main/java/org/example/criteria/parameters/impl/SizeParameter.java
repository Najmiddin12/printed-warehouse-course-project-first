package org.example.criteria.parameters.impl;

import org.example.criteria.parameters.Parameter;
import org.example.entity.Brochure;

public record SizeParameter(String size) implements Parameter<Brochure> {

    @Override
    public boolean test(Brochure product) {
        return size.equals(product.getSize());
    }
}
