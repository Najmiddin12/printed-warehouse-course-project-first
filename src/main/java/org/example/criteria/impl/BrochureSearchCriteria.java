package org.example.criteria.impl;

import org.example.criteria.AbstractCriteria;
import org.example.entity.Brochure;

public class BrochureSearchCriteria extends AbstractCriteria<Brochure> {

    @Override
    public Class<Brochure> getProductType() {
        return Brochure.class;
    }
}