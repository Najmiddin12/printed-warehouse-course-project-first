package org.example.criteria.parameters.impl;

import org.example.criteria.parameters.Parameter;
import org.example.entity.Brochure;

public record PaperTypeParameter(String paperType) implements Parameter<Brochure> {

    @Override
    public boolean test(Brochure product) {
        return paperType.equals(product.getPaperType());
    }
}