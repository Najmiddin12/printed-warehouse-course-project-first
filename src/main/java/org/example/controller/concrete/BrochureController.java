package org.example.controller.concrete;

import org.example.controller.converters.ParameterConverter;
import org.example.criteria.impl.BrochureSearchCriteria;
import org.example.criteria.SearchCriteria;
import org.example.entity.Brochure;
import org.example.service.ProductService;
import java.util.List;

public class BrochureController extends ConcreteController<Brochure> {

    public BrochureController(ProductService productService, List<ParameterConverter<Brochure>> converters) {
        super(productService, converters);
    }

    @Override
    protected SearchCriteria<Brochure> createCriteria() {
        return new BrochureSearchCriteria();
    }
}

