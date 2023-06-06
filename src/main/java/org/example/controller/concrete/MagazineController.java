package org.example.controller.concrete;

import org.example.controller.converters.ParameterConverter;
import org.example.criteria.impl.MagazineSearchCriteria;
import org.example.criteria.SearchCriteria;
import org.example.entity.Magazine;
import org.example.service.ProductService;

import java.util.List;

public class MagazineController extends ConcreteController<Magazine> {

    public MagazineController(ProductService productService, List<ParameterConverter<Magazine>> converters) {
        super(productService, converters);
    }

    @Override
    protected SearchCriteria<Magazine> createCriteria() {
        return new MagazineSearchCriteria();
    }
}

