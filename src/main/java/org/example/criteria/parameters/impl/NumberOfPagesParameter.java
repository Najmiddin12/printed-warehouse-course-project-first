package org.example.criteria.parameters.impl;

import org.example.criteria.parameters.Parameter;
import org.example.entity.Range;
import org.example.entity.Magazine;

public record NumberOfPagesParameter(Range<Integer> numberOfPages) implements Parameter<Magazine> {

    @Override
    public boolean test(Magazine product) {
        return numberOfPages.isIn(product.getNumberOfPages());
    }
}
