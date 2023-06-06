package org.example.criteria.impl;

import org.example.criteria.AbstractCriteria;
import org.example.entity.Magazine;

public class MagazineSearchCriteria extends AbstractCriteria<Magazine> {

    @Override
    public Class<Magazine> getProductType() {
        return Magazine.class;
    }
}